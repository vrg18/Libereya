package edu.vrg18.libereya.service.implementation;

import edu.vrg18.libereya.dto.AuthorDto;
import edu.vrg18.libereya.entity.Author;
import edu.vrg18.libereya.exception.UnlawfulDeletionException;
import edu.vrg18.libereya.repository.AuthorRepository;
import edu.vrg18.libereya.repository.BookRepository;
import edu.vrg18.libereya.service.interfaces.AuthorService;
import edu.vrg18.libereya.utils.HibernateUnproxy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private ModelMapper authorMapper;

    @Autowired
    private void setRepository(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setMapper(ModelMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public AuthorDto getAuthorById(UUID id) {
        return authorMapper.map(HibernateUnproxy.initializeAndUnproxy(authorRepository.getOne(id)), AuthorDto.class);
    }

    @Override
    public AuthorDto createAuthor(AuthorDto authorDto) {
        return authorMapper.map(authorRepository.save(authorMapper.map(authorDto, Author.class)), AuthorDto.class);
    }

    @Override
    public AuthorDto updateAuthor(AuthorDto authorDto) {
        authorRepository.save(authorMapper.map(authorDto, Author.class));
        return authorDto;
    }

    @Override
    public void deleteAuthor(UUID id) {
        validateAuthorBeforeDeleting(id);
        authorRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public List<AuthorDto> findAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(a -> authorMapper.map(a, AuthorDto.class))
                .collect(Collectors.toList());
    }

    private void validateAuthorBeforeDeleting(UUID id) {
        if (!bookRepository.findBooksByAuthorId(id).isEmpty())
            throw new UnlawfulDeletionException(String.format("Author with id %s has books, cannot be deleted", id));
    }
}
