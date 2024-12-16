package com.example.news.controller;

import com.example.news.domain.News;
import com.example.news.dto.NewsDto;
import com.example.news.mapper.NewsMapper;
import com.example.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class NewsController {

    private final NewsRepository newsRepository;
    private final NewsMapper mapper;

    @GetMapping("/news/new")
    public String newArticleForm(){
        return "news/new";
    }

    @PostMapping("/news/create")
    public String createNews(NewsDto.Post post){
        System.out.println(post);
        News n = mapper.newsPostDtoToNews(post);
        System.out.println(n);
        newsRepository.save(n);
        System.out.println(n);
        return "";
    }

    //엔티티를 그대로 사용하는 예시
    @PostMapping("/news/create2")
    public String createNews(News news){
        newsRepository.save(news);
        return "";
    }
}
