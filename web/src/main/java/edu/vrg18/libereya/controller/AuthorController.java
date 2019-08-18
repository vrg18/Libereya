package edu.vrg18.libereya.controller;

import edu.vrg18.libereya.dto.AuthorDto;
import edu.vrg18.libereya.service.interfaces.AuthorService;
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
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public void setService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String listAuthors(Model model) {
        model.addAttribute("title", "Authors");
        List<AuthorDto> authorsDto = authorService.findAllAuthors();
        model.addAttribute("authors", authorsDto);
        return "library/listAuthors";
    }

    @GetMapping("/edit_author/{id}")
    public String editAuthor(@PathVariable UUID id, Model model) {
        model.addAttribute("title", "EditAuthor");
        AuthorDto authorDto = authorService.getAuthorById(id);
        model.addAttribute("author", authorDto);
        return "library/createOrEditAuthor";
    }

    @PostMapping(value = "/save_author", params = "id!=")
    public String updateAuthor(@ModelAttribute("author") AuthorDto authorDto) {
        authorService.updateAuthor(authorDto);
        return "redirect:/authors";
    }

    @GetMapping("/new_author")
    public String newAuthor(Model model) {
        model.addAttribute("title", "NewAuthor");
        model.addAttribute("newAuthor", true);
        return "library/createOrEditAuthor";
    }

    @PostMapping(value = "/save_author", params = "id=")
    public String createAuthor(@ModelAttribute("author") AuthorDto authorDto) {
        authorService.createAuthor(authorDto);
        return "redirect:/authors";
    }

    @GetMapping("/delete_author/{id}")
    public String deleteAuthor(@PathVariable UUID id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }
}
