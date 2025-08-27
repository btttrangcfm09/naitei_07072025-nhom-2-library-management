package com.group2.library_management.repository.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.JoinType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import com.group2.library_management.entity.BookInstance;
import com.group2.library_management.entity.Edition;
import com.group2.library_management.entity.Publisher;
import com.group2.library_management.entity.enums.BookStatus;

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

    /**
     * Tiêu chí: Lọc các Edition được xuất bản theo năm.
     * Edition phải thuộc ÍT NHẤT MỘT trong các năm được cung cấp (OR)
     * 
     * @param years Danh sách năm xuất bản
     * @return Specification
     */
    public static Specification<Edition> hasPublicationYears(List<Integer> years) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(years)) { 
                return null;
            }

            Expression<Integer> yearExpression = criteriaBuilder.function("YEAR", Integer.class, root.get("publicationDate"));
            return yearExpression.in(years); 
        };
    }

    /**
     * Tiêu chí: Lọc các Edition theo nhà xuất bản.
     * Edition phải thuộc ÍT NHẤT MỘT trong các NXB được cung cấp (OR)
     * 
     * @param publisherIds Danh sách ID của nhà xuất bản
     * @return Specification
     */
    public static Specification<Edition> hasPublishers(List<Integer> publisherIds) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(publisherIds)) {
                return null;
            }

            Join<Edition, Publisher> publisherJoin = root.join("publisher", JoinType.INNER);
            return publisherJoin.get("id").in(publisherIds); 
        };
    }

    /**
     * Tiêu chí: Lọc các Edition theo ngôn ngữ.
     * So sánh không phân biệt hoa thường.
     * 
     * @param languages Danh sách tên ngôn ngữ
     * @return Specification
     */
    public static Specification<Edition> hasLanguages(List<String> languages) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(languages)) {
                return null;
            }

            List<String> lowerCaseLanguages = languages.stream()
                                                       .map(String::toLowerCase)
                                                       .map(String::trim)
                                                       .collect(Collectors.toList());
            return criteriaBuilder.lower(root.get("language")).in(lowerCaseLanguages);
        };
    }
}
