package com.group2.library_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.group2.library_management.entity.BookInstance;
import com.group2.library_management.entity.enums.BookStatus;

@Repository
public interface BookInstanceRepository extends ListCrudRepository<BookInstance, Integer> {
    Boolean existsByBarcode(String barcode);
    List<BookInstance> findByEditionIdOrderByAcquiredDateDesc(Integer editionId);

    @Modifying
    @Query("DELETE FROM BookInstance b WHERE b.id = :id")
    void permanentlyDeleteById(@Param("id") Integer id);

    List<BookInstance> findByStatus(BookStatus status);
}
