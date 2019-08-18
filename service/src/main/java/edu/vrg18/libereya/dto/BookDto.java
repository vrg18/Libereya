package edu.vrg18.libereya.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDto {

    private UUID id;
    private AuthorDto author;
    private String title;
    private int year;
    private String genre;
}
