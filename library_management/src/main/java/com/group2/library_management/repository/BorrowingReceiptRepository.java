package com.group2.library_management.repository;

import com.group2.library_management.entity.BorrowingReceipt;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingReceiptRepository extends JpaRepository<BorrowingReceipt, Integer>,
        JpaSpecificationExecutor<BorrowingReceipt> {

    @EntityGraph(attributePaths = {
            "user",
            "borrowingDetails.bookInstance.edition"
    })
    Optional<BorrowingReceipt> findById(Integer id);
}
