package com.group2.library_management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.group2.library_management.entity.BorrowingReceipt;
import com.group2.library_management.entity.enums.BorrowingStatus;

@Repository
public interface BorrowingReceiptRepository extends JpaRepository<BorrowingReceipt, Integer>,
                JpaSpecificationExecutor<BorrowingReceipt> {

        @EntityGraph(attributePaths = {
                        "user",
                        "borrowingDetails.bookInstance.edition"
        })

        Optional<BorrowingReceipt> findById(Integer id);

        @EntityGraph(attributePaths = {
                        "borrowingDetails.bookInstance"
        })
        List<BorrowingReceipt> findByStatus(BorrowingStatus status);

        @Query("""
            SELECT br 
            FROM BorrowingReceipt br
            LEFT JOIN FETCH br.borrowingDetails bd
            LEFT JOIN FETCH bd.bookInstance bi
            WHERE br.status = :status AND br.dueDate < CURRENT_TIMESTAMP
            """)
        List<BorrowingReceipt> findOverdueReceiptsByStatus(@Param("status") BorrowingStatus status);
}
