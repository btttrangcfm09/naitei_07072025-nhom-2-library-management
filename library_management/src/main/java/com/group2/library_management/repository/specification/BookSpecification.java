package com.group2.library_management.repository.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.group2.library_management.entity.Author;
import com.group2.library_management.entity.AuthorBook;
import com.group2.library_management.entity.Book;
import com.group2.library_management.entity.BookGenre;

public class BookSpecification {
    
    /**
     * Tiêu chí: Tìm kiếm `keyword` trong TITLE của sách HOẶC trong NAME của tác giả.
     * Tìm kiếm không phân biệt hoa thường và bỏ qua khoảng trắng thừa.
     * @param keyword Chuỗi tìm kiếm
     * @return Specification
     */
    public static Specification<Book> searchByKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(keyword)) {
                return null;
            }

            // Xử lý chuỗi keyword: chuyển sang chữ thường và loại bỏ khoảng trắng
            String pattern = "%" + keyword.toLowerCase().trim() + "%";

            // Join với bảng Author để tìm theo tên tác giả.
            // Sử dụng LEFT JOIN để không loại bỏ những cuốn sách chưa có tác giả.
            Join<Book, AuthorBook> authorBookJoin = root.join("authorBooks", JoinType.LEFT);
            Join<AuthorBook, Author> authorJoin = authorBookJoin.join("author", JoinType.LEFT);
            
            // Điều kiện 1: title LIKE %keyword% (không phân biệt hoa thường)
            Predicate titlePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), pattern);

            // Điều kiện 2: author.name LIKE %keyword% (không phân biệt hoa thường)
            Predicate authorNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(authorJoin.get("name")), pattern);

            query.distinct(true);

            // Kết hợp hai điều kiện bằng OR
            return criteriaBuilder.or(titlePredicate, authorNamePredicate);
        };
    }

    /**
     * Tiêu chí: Lọc theo danh sách genreId
     * Sách phải thuộc ÍT NHẤT MỘT trong các genre được cung cấp (OR)
     * 
     * @param genreIds Danh sách id của genre
     * @return Specification
     */
    public static Specification<Book> hasAnyGenre(List<Integer> genreIds) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(genreIds)) {
                return null;
            }
            query.distinct(true);

            Join<Book, BookGenre> bookGenreJoin = root.join("bookGenres");

            return bookGenreJoin.get("genre").get("id").in(genreIds);
        };
    }

    /**
     * Tiêu chí: Lọc sách có TẤT CẢ các thể loại được cung cấp (AND).
     * 
     * @param genreIds Danh sách id của genre
     * @return Specification
     */
    public static Specification<Book> hasAllGenres(List<Integer> genreIds) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(genreIds)) {
                return null;
            }

            query.distinct(true);

            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Book> subRoot = subquery.from(Book.class);
            Join<Book, BookGenre> subBookGenreJoin = subRoot.join("bookGenres");

            subquery.select(subRoot.get("id"))
                    .where(subBookGenreJoin.get("genre").get("id").in(genreIds))
                    .groupBy(subRoot.get("id"))
                    .having(
                        criteriaBuilder.equal(
                            criteriaBuilder.count(subBookGenreJoin.get("genre").get("id")), 
                            (long) genreIds.size() 
                        )
                    );

            return root.get("id").in(subquery);
        };
    }

    /**
     * Tiêu chí: Lọc theo danh sách authorId
     * Sách phải được viết bởi ÍT NHẤT MỘT trong các tác giả được cung cấp (OR)
     * 
     * @param authorIds danh sách id của tác giả
     * @return Specification
     */
    public static Specification<Book> hasAnyAuthor(List<Integer> authorIds) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(authorIds)) {
                return null;
            }
            query.distinct(true);

            Join<Book, AuthorBook> authorBookJoin = root.join("authorBooks");

            return authorBookJoin.get("author").get("id").in(authorIds);
        };
    }

    /**
     * Tiêu chí: Lọc sách được viết bởi TẤT CẢ các tác giả được cung cấp (AND).
     * 
     * @param authorIds Danh sách ID của các tác giả.
     * @return Specification
     */
    public static Specification<Book> hasAllAuthors(List<Integer> authorIds) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(authorIds)) {
                return null;
            }
            query.distinct(true);

            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Book> subRoot = subquery.from(Book.class);
            Join<Book, AuthorBook> subAuthorBookJoin = subRoot.join("authorBooks");

            subquery.select(subRoot.get("id")) 
                    .where(subAuthorBookJoin.get("author").get("id").in(authorIds))
                    .groupBy(subRoot.get("id")) 
                    .having(
                        criteriaBuilder.equal(
                            criteriaBuilder.count(subAuthorBookJoin.get("author").get("id")), 
                            (long) authorIds.size() 
                        )
                    );

            return root.get("id").in(subquery);
        };
    }
}
