package com.example.news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloUserController {

    @GetMapping("/hello")
    public String helloUser(Model model){
        model.addAttribute("userName", "홍길동");
        return "news/helloUser";
    }
}
