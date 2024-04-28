package edu.miu.cs489.tsogt.backend.model;

import edu.miu.cs489.tsogt.backend.enums.BookStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    @NotBlank(message = "* title is required")
    private String title;
    @Column(nullable = false)
    @NotBlank(message = "* author is required")
    private String author;
    @Column(nullable = false)
    @NotBlank(message = "* genre is required")
    private String genre;
    @Column(nullable = false)
    @NotBlank(message = "* publisher is required")
    private String publisher;
    private String description;
    @Column(nullable = false)
    @NotNull(message = "* year is required")
    private int year;
    @Column(nullable = false)
    private BookStatus status = BookStatus.AVAILABLE;

    @ManyToOne
    private User owner;

    public Book(String title, String author, String genre, String publisher, String description, int year, BookStatus status, User owner) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.description = description;
        this.year = year;
        this.status = status;
        this.owner = owner;
    }
}
