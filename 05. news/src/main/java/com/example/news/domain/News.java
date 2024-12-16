package com.example.news.domain;

import com.example.news.dto.NewsDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

//    public static News toEntity(NewsDto.Post post){
//        return new News(null, post.getTitle(), post.getContent());
//
//    }
}
