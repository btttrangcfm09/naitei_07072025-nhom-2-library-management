package com.group2.library_management.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.apache.poi.ss.usermodel.*;

import com.group2.library_management.exception.InvalidColumnFormatException;

// File Util này để xử lý các thao tác liên quan đến đọc file Excel
public final class ExcelUtil {
    private ExcelUtil() {}

    // đọc ngày tháng -> chuyển đổi sang LocalDate
    public static LocalDate getCellValueAsLocalDate(Cell cell, String columnName) throws InvalidColumnFormatException {
        // Nếu cột này trống
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }

        // Nếu cột này là kiểu ngày tháng
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue().toLocalDate();
        } 
    
        // Nếu cột này là kiểu chuỗi
        if (cell.getCellType() == CellType.STRING) {
            try {
                return LocalDate.parse(cell.getStringCellValue(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                throw new InvalidColumnFormatException(columnName, "dd/MM/yyyy" );
            }
        }

        // Nếu không rơi vào các trường hợp trên
        throw new InvalidColumnFormatException(columnName, "dd/MM/yyyy");
   }

   // Đọc cột có chứa kiểu dữ liệu BigDecimal
   public static BigDecimal getCellValueAsBigDecimal(Cell cell, String columnName) throws InvalidColumnFormatException {
        // Nếu là Ô trống -> trả về BigDecimal.ZERO 
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }

        // Nếu là Numeric -> chuyển sang BigDecimal
        if (cell.getCellType() == CellType.NUMERIC) {
            return new BigDecimal(String.valueOf(cell.getNumericCellValue()));
        }
    
        // Trường hợp còn lại -> ném exception
        throw new InvalidColumnFormatException(columnName, "số");
   }

    // Đọc cột có chứa kiểu dữ liệu String
    public static String getCellValueAsString(Cell cell, String columnName) throws InvalidColumnFormatException {
        // Nếu là Ô trống -> trả về chuỗi rỗng
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }
        // Nếu là kiểu String -> trả về giá trị chuỗi
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        }
        // Trường hợp còn lại -> ném exception
        throw new InvalidColumnFormatException(columnName, "chuỗi");
    }
}


