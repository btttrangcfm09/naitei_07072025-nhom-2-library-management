package com.group2.library_management.repository;

import com.group2.library_management.entity.BorrowingReceipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowingHistoryRepository extends JpaRepository<BorrowingReceipt, Integer>, JpaSpecificationExecutor<BorrowingReceipt> {

    @Override
    @EntityGraph(attributePaths = {"borrowingDetails.bookInstance.edition.book"})
    Page<BorrowingReceipt> findAll(Specification<BorrowingReceipt> spec, Pageable pageable);
}
