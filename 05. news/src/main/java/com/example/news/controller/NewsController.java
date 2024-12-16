package com.example.news.controller;

import com.example.news.domain.News;
import com.example.news.dto.NewsDto;
import com.example.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping("/news/new")
    public String newArticleForm(){
        return "news/new";
    }

    @PostMapping("/news/create")
    public String createNews(NewsDto.Post post){
        System.out.println(post);
        News n = News.toEntity(post);
        System.out.println(n);
        newsRepository.save(n);
        return "";
    }
}
