package edu.vrg18.libereya.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.vrg18.libereya.dto.AuthorDto;
import edu.vrg18.libereya.service.interfaces.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/rest/authors")
public class AuthorRestController {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private AuthorService authorService;

    @Autowired
    public void setService(AuthorService authorService) {
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
    public AuthorDto createAuthor(@RequestBody @Valid Map<String, String> authorMap) {
        return authorService.createAuthor(objectMapper.convertValue(authorMap, AuthorDto.class));
    }

    @PutMapping("/{id}")
    public AuthorDto updateAuthor(@PathVariable UUID id, @RequestBody @Valid Map<String, String> authorMap) {
        return authorService.updateAuthor(new AuthorDto(id, authorMap.get("name")));
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable UUID id) {
        authorService.deleteAuthor(id);
    }
}
