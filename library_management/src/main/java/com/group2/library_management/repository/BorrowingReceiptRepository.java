package com.group2.library_management.repository;

import com.group2.library_management.entity.BorrowingReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowingReceiptRepository extends JpaRepository<BorrowingReceipt, Integer>,
        JpaSpecificationExecutor<BorrowingReceipt> {
}
