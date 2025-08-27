package com.group2.library_management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.group2.library_management.entity.Edition;

@Repository
public interface EditionRepository extends JpaRepository<Edition, Integer>, JpaSpecificationExecutor<Edition> {
    Optional<Edition> findByIsbn(String isbn);
    Boolean existsByIsbn(String isbn);

    List<Edition> findByBookIdOrderByPublicationDateDesc(Integer bookId);

    /**
     * Hard delete an Edition by ID.
     * @param id ID of the Edition to delete.
     */
    @Modifying
    @Query("DELETE FROM Edition e WHERE e.id = :id")
    void hardDeleteById(Integer id);
}
