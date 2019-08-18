package edu.vrg18.libereya.service.interfaces;

import edu.vrg18.libereya.dto.BookDto;

import java.util.List;
import java.util.UUID;

public interface BookService {
    BookDto getBookById(UUID id);
    BookDto createBook(BookDto bookDto);
    BookDto updateBook(BookDto bookDto);//UUID id, UUID authorId, String title, int year, String genre);
    void deleteBook(UUID id);
    List<BookDto> findAllBooks();
    List<BookDto> findBooksByAuthor(UUID id);
}
