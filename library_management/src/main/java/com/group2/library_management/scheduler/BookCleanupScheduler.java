package com.group2.library_management.scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.group2.library_management.entity.BookInstance;
import com.group2.library_management.entity.enums.BookStatus;
import com.group2.library_management.repository.BookInstanceRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookCleanupScheduler {

    private static final Logger logger = LoggerFactory.getLogger(BookCleanupScheduler.class);
    private final BookInstanceRepository bookInstanceRepository;

    
    // This job runs at 1am on the first day of every month.

    @Scheduled(cron = "0 0 1 1 * ?")
    @Transactional
    public void softDeleteArchivedBooks() {
        logger.info("Bắt đầu công việc xóa mềm các sách đã lưu trữ...");

        // 1. Find all books with ARCHIVED status
        List<BookInstance> archivedBooks = bookInstanceRepository.findByStatus(BookStatus.ARCHIVED);

        if (archivedBooks.isEmpty()) {
            logger.info("Không có sách nào ở trạng thái ARCHIVED để xóa.");
            return;
        }

        // 2. Browse through the list and perform a soft delete
        logger.info("Tìm thấy {} sách cần xóa mềm.", archivedBooks.size());
        for (BookInstance book : archivedBooks) {
            bookInstanceRepository.delete(book);
        }
        
        logger.info("Hoàn thành công việc xóa mềm sách.");
    }
}
