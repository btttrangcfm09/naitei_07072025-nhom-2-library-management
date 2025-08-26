package com.group2.library_management.scheduler;

import com.group2.library_management.entity.BorrowingReceipt;
import com.group2.library_management.entity.enums.BookStatus;
import com.group2.library_management.entity.enums.BorrowingStatus;
import com.group2.library_management.repository.BorrowingReceiptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OverdueScheduler {

    private final BorrowingReceiptRepository borrowingReceiptRepository;

    /**
     * Scheduled task to check for and update overdue borrowing receipts.
     * This runs every day at 00:30 (AM).
     */
    @Scheduled(cron = "0 30 0 * * ?")
    @Transactional
    public void checkAndUpdateOverdueReceiptsDaily() {
        log.info("Starting daily check for overdue borrowing receipts...");
        try {
            List<BorrowingReceipt> borrowedReceipts = borrowingReceiptRepository.findByStatus(BorrowingStatus.BORROWED);
            int updatedCount = processOverdueChecks(borrowedReceipts, "daily");
            log.info("Finished daily overdue check. Updated {} receipts to OVERDUE status.", updatedCount);
        } catch (Exception e) {
            log.error("Error during the daily check for overdue receipts.", e);
        }
    }

    /**
     * A non-scheduled method to manually trigger an overdue check.
     * Can be used for ad-hoc or more frequent checks if needed.
     */
    @Transactional
    public void checkOverdueHourly() {
        log.debug("Starting hourly check for overdue receipts...");
        try {
            List<BorrowingReceipt> borrowedReceipts = borrowingReceiptRepository.findByStatus(BorrowingStatus.BORROWED);
            int updatedCount = processOverdueChecks(borrowedReceipts, "hourly");
            if (updatedCount > 0) {
                log.info("Finished hourly overdue check. Updated {} receipts to OVERDUE status.", updatedCount);
            }
        } catch (Exception e) {
            log.error("Error during the hourly check for overdue receipts.", e);
        }
    }

    /**
     * Core logic for checking a list of receipts and updating their status to OVERDUE if applicable.
     *
     * @param receipts The list of BorrowingReceipt entities to process.
     * @param context  A string describing the context of the check (e.g., "daily", "hourly") for logging purposes.
     * @return The number of receipts that were updated.
     */
    private int processOverdueChecks(List<BorrowingReceipt> receipts, String context) {
        LocalDateTime now = LocalDateTime.now();
        int updatedCount = 0;

        for (BorrowingReceipt receipt : receipts) {
            if (receipt.getDueDate() != null && now.isAfter(receipt.getDueDate())) {
                // Check if there are any books still in BORROWED status for this receipt.
                boolean hasActiveBorrowedBooks = receipt.getBorrowingDetails().stream()
                        .anyMatch(detail -> detail.getBookInstance().getStatus() == BookStatus.BORROWED);

                if (hasActiveBorrowedBooks) {
                    receipt.setStatus(BorrowingStatus.OVERDUE);
                    borrowingReceiptRepository.save(receipt);
                    updatedCount++;
                    log.debug("({} check) Updated receipt ID {} to OVERDUE.", context, receipt.getId());
                }
            }
        }
        return updatedCount;
    }
}
