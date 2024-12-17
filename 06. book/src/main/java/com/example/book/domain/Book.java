package com.example.book.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String title;

    @Column(nullable = false, length = 45)
    private String subTitle;

    @Column(length = 45)
    private String author;

    @Column(length = 45)
    private String publisher;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status{
        BORROWED, AVAILABLE
    }
}
