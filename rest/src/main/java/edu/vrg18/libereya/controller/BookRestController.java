package edu.vrg18.libereya.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.vrg18.libereya.dto.BookDto;
import edu.vrg18.libereya.service.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/rest/books")
public class BookRestController {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private BookService bookService;

    @Autowired
    public void setService(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDto> listBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/authors/{id}")
    public List<BookDto> listBooksByAuthor(@PathVariable UUID id) {
        return bookService.findBooksByAuthor(id);
    }

    @GetMapping("/{id}")
    public BookDto oneBook(@PathVariable UUID id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public BookDto createBook(@RequestBody Map<String, String> bookMap) {
        return bookService.updateBook(objectMapper.convertValue(bookMap, BookDto.class));
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@RequestBody Map<String, String> bookMap) {
        return bookService.updateBook(objectMapper.convertValue(bookMap, BookDto.class));
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
    }
}
