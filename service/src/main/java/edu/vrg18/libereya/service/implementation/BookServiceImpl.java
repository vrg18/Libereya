package edu.vrg18.libereya.service.implementation;

import edu.vrg18.libereya.dto.AuthorDto;
import edu.vrg18.libereya.dto.BookDto;
import edu.vrg18.libereya.entity.Author;
import edu.vrg18.libereya.entity.Book;
import edu.vrg18.libereya.repository.AuthorRepository;
import edu.vrg18.libereya.repository.BookRepository;
import edu.vrg18.libereya.service.interfaces.BookService;
import edu.vrg18.libereya.utils.HibernateUnproxy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class BookServiceImpl implements BookService {

    private static final String AUTHOR_UNKNOWN = "(автор неизвестен)";

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private ModelMapper bookMapper;
    private ModelMapper authorMapper;

    @Autowired
    private void setRepository(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Autowired
    public void setMapper(ModelMapper bookMapper, ModelMapper authorMapper) {
        this.bookMapper = bookMapper;
        this.authorMapper = authorMapper;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public BookDto getBookById(UUID id) {
        return bookMapper.map(HibernateUnproxy.initializeAndUnproxy(bookRepository.getOne(id)), BookDto.class);
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        if(bookDto.getAuthor().getId() == null) {
            Author author = authorRepository.findByName(AUTHOR_UNKNOWN);
            if (author == null) {
                author = new Author(AUTHOR_UNKNOWN);
                authorRepository.save(author);
            }
            bookDto.setAuthor(authorMapper.map(author, AuthorDto.class));
        }
        return bookMapper.map(bookRepository.save(bookMapper.map(bookDto, Book.class)), BookDto.class);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        bookRepository.save(bookMapper.map(bookDto, Book.class));
        return bookDto;
    }

    @Override
    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<BookDto> findAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(a -> bookMapper.map(a, BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<BookDto> findBooksByAuthor(UUID id) {
        return bookRepository.findBooksByAuthorId(id)
                .stream()
                .map(a -> bookMapper.map(a, BookDto.class))
                .collect(Collectors.toList());
    }
}
