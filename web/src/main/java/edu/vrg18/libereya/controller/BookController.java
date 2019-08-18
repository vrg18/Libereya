package edu.vrg18.libereya.controller;

import edu.vrg18.libereya.dto.AuthorDto;
import edu.vrg18.libereya.dto.BookDto;
import edu.vrg18.libereya.service.interfaces.AuthorService;
import edu.vrg18.libereya.service.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class BookController {

    private BookService bookService;
    private AuthorService authorService;

    @Autowired
    public void setService(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("title", "Books");
        List<BookDto> booksDto = bookService.findAllBooks();
        model.addAttribute("books", booksDto);
        return "library/listBooks";
    }

    @GetMapping("/books_author/{id}")
    public String listBooksByAuthor(@PathVariable UUID id, Model model) {
        model.addAttribute("title", "BooksByAuthor");
        List<BookDto> booksDto = bookService.findBooksByAuthor(id);
        model.addAttribute("books", booksDto);
        return "library/listBooks";
    }

    @GetMapping("/edit_book/{id}")
    public String editBook(@PathVariable UUID id, Model model) {
        model.addAttribute("title", "EditBook");
        BookDto bookDto = bookService.getBookById(id);
        model.addAttribute("book", bookDto);
        List<AuthorDto> authorsDto = authorService.findAllAuthors();
        model.addAttribute("authors", authorsDto);
        return "library/createOrEditBook";
    }

    @PostMapping(value = "/save_book", params = "id!=")
    public String updateBook(@ModelAttribute("book") BookDto bookDto) {
        bookService.updateBook(bookDto);
        return "redirect:/books";
    }

    @GetMapping("/new_book")
    public String newBook(Model model) {
        model.addAttribute("title", "NewBook");
        List<AuthorDto> authorsDto = authorService.findAllAuthors();
        model.addAttribute("authors", authorsDto);
        model.addAttribute("newBook", true);
        return "library/createOrEditBook";
    }

    @PostMapping(value = "/save_book", params = "id=")
    public String createBook(@ModelAttribute("book") BookDto bookDto) {
        bookService.createBook(bookDto);
        return "redirect:/books";
    }

    @GetMapping("/delete_book/{id}")
    public String deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
