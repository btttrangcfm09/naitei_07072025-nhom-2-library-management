package com.group2.library_management.repository;

import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import com.group2.library_management.entity.Edition;

@Repository
public interface EditionRepository extends ListCrudRepository<Edition, Integer> {
    Optional<Edition> findByIsbn(String isbn);
    Boolean existsByIsbn(String isbn);
}
