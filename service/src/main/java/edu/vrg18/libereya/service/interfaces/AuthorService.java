package edu.vrg18.libereya.service.interfaces;

import edu.vrg18.libereya.dto.AuthorDto;

import java.util.List;
import java.util.UUID;

public interface AuthorService {
    AuthorDto getAuthorById(UUID id);
    AuthorDto createAuthor(AuthorDto authorDto);
    AuthorDto updateAuthor(AuthorDto authorDto);
    void deleteAuthor(UUID id);
    List<AuthorDto> findAllAuthors();
}
