package com.example.library_project.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import java.time.Year;
import java.util.Date;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "title")
    private String book_title;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "author")
    private String author;
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;
    @Column(name = "image", columnDefinition = "LONGTEXT")
    private String image;
    @Column(name = "date")
    private Year book_date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
