package com.example.jpa.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    @Column(name = "news_title", nullable = false, length = 255)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

}
