package com.group2.library_management.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import com.group2.library_management.entity.BookInstance;

@Repository
public interface BookInstanceRepository extends ListCrudRepository<BookInstance, Integer> {
    Boolean existsByBarcode(String barcode);
}
