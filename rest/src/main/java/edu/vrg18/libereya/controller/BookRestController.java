package edu.vrg18.libereya.controller;

import edu.vrg18.libereya.dto.BookDto;
import edu.vrg18.libereya.service.interfaces.BookService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
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
    public BookDto createBook(@RequestBody @Valid BookDto bookDto) {
        return bookService.updateBook(bookDto);
    }

    @PutMapping
    public BookDto updateBook(@RequestBody @Valid BookDto bookDto) {
        return bookService.updateBook(bookDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
    }
}
