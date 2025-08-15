package com.group2.library_management.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.group2.library_management.exception.ImportValidationException;
import org.springframework.beans.factory.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public abstract class ExcelImportService<T> {
    private final MessageSource messageSource;

    @Autowired
    public ExcelImportService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    @Transactional(rollbackFor = Exception.class)
    public void importFromFile(MultipartFile file) throws ImportValidationException, IOException {
        // Kiểm tra định dạng file
        validateFile(file);

        List<T> entities = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        // Tạo một Set để theo dõi các barcode đã xử lý trong file
        Set<String> processedUniqueItems = new HashSet<>();

        try (InputStream inputStream = file.getInputStream();
                XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowNumber = 1; // Bắt đầu từ 1 để khớp với số dòng trên Excel
            for (Row row : sheet) {
                if (rowNumber++ == 1)
                    continue; // Bỏ qua dòng tiêu dề

                try {
                    // mapRowToEntity sẽ ném ra Exception nếu có lỗi validation
                    T entity = mapRowToEntity(row, rowNumber - 1, processedUniqueItems);
                    entities.add(entity);
                } catch (Exception e) {
                    errors.add(String.format("Dòng %d: %s", rowNumber - 1, e.getMessage()));
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new ImportValidationException(errors);
        }

        saveAll(entities);
    }

    // Lớp con phải định nghĩa cách ánh xạ một dòng Excel thành một đối tượng.
    // Ném Exception nếu có lỗi validation.
    // @param row Dòng hiện tại
    // @param rowNumber Số thứ tự dòng (để báo lỗi)
    // @param processedBarcodes Set chứa các barcode đã xử lý để kiểm tra trùng lặp
    // @return Đối tượng T đã được tạo và validate
    protected abstract T mapRowToEntity(Row row, int rowNumber, Set<String> processedUniqueItems) throws Exception;

    // Lớp con phải định nghĩa cách lưu danh sách các đối tượng.
    // @param entities Danh sách các đối tượng cần lưu
    protected abstract void saveAll(List<T> entities);

    protected void validateFile(MultipartFile file) throws ImportValidationException {
        Locale locale = LocaleContextHolder.getLocale();
        String filename = file.getOriginalFilename();   
        if (filename == null || !filename.toLowerCase().endsWith(".xlsx")) {
            String errorMessage = messageSource.getMessage("error.file.invalid", null, locale);
            throw new ImportValidationException(List.of(errorMessage));
        }
        String contentType = file.getContentType();
        if (!"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(contentType)) {
            String errorMessage = messageSource.getMessage("error.file.invalid", null, locale);
            throw new ImportValidationException(List.of(errorMessage));
        }       
        if (file.isEmpty()) {
            String errorMessage = messageSource.getMessage("error.file.required", null, locale);
            throw new ImportValidationException(List.of(errorMessage));
        }
    }
}
