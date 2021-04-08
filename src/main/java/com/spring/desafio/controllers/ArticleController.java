package com.spring.desafio.controllers;

import com.spring.desafio.entities.DTO.ArticlesDTO;
import com.spring.desafio.services.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    ArticleService service = new ArticleService();

    @GetMapping
    public ResponseEntity filter (@RequestParam Map<String,String> filters) throws Exception {
        ArticlesDTO articles = new ArticlesDTO();
        if (filters.size() == 0) {
            articles = service.getAllArticles();
        }
        else {
            articles = service.getArticlesByFilters(filters);
        }
        return new ResponseEntity(articles, HttpStatus.OK);
    }

}