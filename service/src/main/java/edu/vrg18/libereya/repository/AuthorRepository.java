package edu.vrg18.libereya.repository;

import edu.vrg18.libereya.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

    List<Author> findAll();
    Optional<Author> findById(UUID id);
    Author findByName(String name);
}
