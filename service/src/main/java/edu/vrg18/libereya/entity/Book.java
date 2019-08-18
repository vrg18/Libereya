package edu.vrg18.libereya.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "author_id")
    private Author author;

    private String title;

    private int year;

    private String genre;

    public Book(Author author, String title, int year, String genre) {
        this.author = author;
        this.title = title;
        this.year = year;
        this.genre = genre;
    }
}
