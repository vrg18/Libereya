package edu.vrg18.libereya.controller;

import edu.vrg18.libereya.dto.AuthorDto;
import edu.vrg18.libereya.service.interfaces.AuthorService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/authors")
public class AuthorRestController {

    private final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorDto> listAuthors() {
        return authorService.findAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorDto oneAuthor(@PathVariable UUID id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    public AuthorDto createAuthor(@RequestBody @Valid AuthorDto authorDTO) {
        return authorService.createAuthor(authorDTO);
    }

    @PutMapping
    public AuthorDto updateAuthor(@RequestBody @Valid AuthorDto authorDTO) {
        return authorService.updateAuthor(authorDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable UUID id) {
        authorService.deleteAuthor(id);
    }
}
