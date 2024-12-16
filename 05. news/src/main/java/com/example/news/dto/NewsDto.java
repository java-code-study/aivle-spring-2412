package com.example.news.dto;

import lombok.*;

public class NewsDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private String title;
        private String content;
    }
}
