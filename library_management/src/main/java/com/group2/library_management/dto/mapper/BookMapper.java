package com.group2.library_management.dto.mapper;

import com.group2.library_management.dto.response.BookResponse;
import com.group2.library_management.entity.Author;
import com.group2.library_management.entity.AuthorBook;
import com.group2.library_management.entity.Book;
import com.group2.library_management.entity.BookGenre;
import com.group2.library_management.entity.Genre;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE 
)
public interface BookMapper {

    /**
     * Chuyển đổi từ Book entity sang BookResponse DTO.
     * @param book Entity Book
     * @return DTO BookResponse
     */
    @Mapping(source = "authorBooks", target = "authors", qualifiedByName = "authorBooksToAuthorNames")
    @Mapping(source = "bookGenres", target = "genres", qualifiedByName = "bookGenresToGenreNames")
    @Mapping(target = "editionCount", expression = "java(book.getEditions() != null ? book.getEditions().size() : 0)")
    BookResponse toBookResponse(Book book);

    /**
     * Chuyển đổi List<AuthorBook> sang List<String>.
     */
    @Named("authorBooksToAuthorNames")
    default List<String> authorBooksToAuthorNames(List<AuthorBook> authorBooks) {
        if (authorBooks == null) {
            return null;
        }
        return authorBooks.stream()
                .map(AuthorBook::getAuthor)
                .map(Author::getName)
                .toList();
    }

    /**
     * Chuyển đổi List<BookGenre> sang List<String>.
     */
    @Named("bookGenresToGenreNames")
    default List<String> bookGenresToGenreNames(List<BookGenre> bookGenres) {
        if (bookGenres == null) {
            return null;
        }
        return bookGenres.stream()
                .map(BookGenre::getGenre)
                .map(Genre::getName)
                .toList();
    }
}
