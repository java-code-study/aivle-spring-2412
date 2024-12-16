package com.example.news.controller;

import com.example.news.domain.News;
import com.example.news.dto.NewsDto;
import com.example.news.mapper.NewsMapper;
import com.example.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {

    private final NewsRepository newsRepository;
    private final NewsMapper mapper;

    @GetMapping("/new")
    public String newArticleForm(){
        return "news/new";
    }

    @PostMapping("/create")
    public String createNews(NewsDto.Post post){
        System.out.println(post);
        News n = mapper.newsPostDtoToNews(post);
        System.out.println(n);
        newsRepository.save(n);
        System.out.println(n);
        return "redirect:/news/list";
    }

    @GetMapping("/{newsId}")
    public String getNews(@PathVariable("newsId") Long newsId, Model model){
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        model.addAttribute("news", news);
        return "news/detail";
    }

    @GetMapping("/list")
    public String getNewsList(Model model){
        List<News> newsList = newsRepository.findAll();
        model.addAttribute("newsList", newsList);
        return "news/list";
    }

    //엔티티를 그대로 사용하는 예시
    @PostMapping("/create2")
    public String createNews(News news){
        newsRepository.save(news);
        return "";
    }
}
