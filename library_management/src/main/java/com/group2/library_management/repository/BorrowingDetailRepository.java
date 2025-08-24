package com.group2.library_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group2.library_management.entity.BorrowingDetail;

@Repository
public interface BorrowingDetailRepository extends JpaRepository<BorrowingDetail, Integer>{
    boolean existsByBookInstanceId(Integer bookInstanceId);
    List<BorrowingDetail> findByBookInstanceId(Integer bookInstanceId);

    List<BorrowingDetail> findByBorrowingReceiptId(Integer borrowingReceiptId);
} 
