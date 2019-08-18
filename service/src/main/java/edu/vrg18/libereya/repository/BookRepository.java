package edu.vrg18.libereya.repository;

import edu.vrg18.libereya.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findAll();
    List<Book> findBooksByAuthorId(UUID id);
    Optional<Book> findById(UUID id);
}
