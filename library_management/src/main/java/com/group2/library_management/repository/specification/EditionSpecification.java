package com.group2.library_management.repository.specification;

import jakarta.persistence.criteria.Join;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.group2.library_management.entity.BookInstance;
import com.group2.library_management.entity.Edition;
import com.group2.library_management.entity.Publisher;
import com.group2.library_management.entity.enums.BookStatus;

import jakarta.persistence.criteria.Predicate;

public class EditionSpecification {
    public static Specification<Edition> distinct() {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.conjunction();
        };
    }
    
    public static Specification<Edition> searchEditionByKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return cb.conjunction();
            }

            String pattern = "%" + keyword.toLowerCase().trim() + "%";
            Join<Edition, Publisher> editionPublisherJoin = root.join("publisher");

            Predicate titleLike = cb.like(cb.lower(root.get("title")), pattern);
            Predicate isbnLike = cb.like(cb.lower(root.get("isbn")), pattern);
            Predicate publisherNameLike = cb.like(cb.lower(editionPublisherJoin.get("name")), pattern);

            return cb.or(titleLike, isbnLike, publisherNameLike);
        };
    }

    public static Specification<Edition> searchByBookInstanceFields(
            String keyword, BookStatus bookStatus, LocalDate fromDate, LocalDate toDate) {

        return (root, query, cb) -> {
            if ((keyword == null || keyword.trim().isEmpty()) && bookStatus == null && fromDate == null
                    && toDate == null) {
                return cb.conjunction();
            }

            Join<Edition, BookInstance> bookInstanceJoin = root.join("bookInstances");
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.trim().isEmpty()) {
                String pattern = "%" + keyword.toLowerCase().trim() + "%";
                Predicate barcodeLike = cb.like(cb.lower(bookInstanceJoin.get("barcode")), pattern);
                Predicate callNumberLike = cb.like(cb.lower(bookInstanceJoin.get("callNumber")), pattern);
                predicates.add(cb.or(barcodeLike, callNumberLike));
            }

            if (bookStatus != null) {
                predicates.add(cb.equal(bookInstanceJoin.get("status"), bookStatus));
            }

            if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(bookInstanceJoin.get("acquiredDate"), fromDate));
            }

            if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(bookInstanceJoin.get("acquiredDate"), toDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
