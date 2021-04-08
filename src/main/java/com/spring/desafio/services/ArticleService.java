package com.spring.desafio.services;

import com.spring.desafio.entities.Article;
import com.spring.desafio.entities.DTO.ArticleDTO;
import com.spring.desafio.entities.DTO.ArticlesDTO;
import com.spring.desafio.repositories.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ArticleService {

    ArticleRepository repository = new ArticleRepository();

    public ArticlesDTO getAllArticles () throws Exception {
        ArticlesDTO articles = new ArticlesDTO();
        articles.setArticles(mapListEntityToDTO(repository.getAllArticlesFromXlsx()));
        return articles;
    }

    public ArticlesDTO getArticlesByFilters (Map<String,String> filters) throws Exception {
        ArticlesDTO articlesDTO = new ArticlesDTO();
        articlesDTO.setArticles(mapListEntityToDTO(repository.getArticlesByFilters(filters)));
        return articlesDTO;
    }

    public List<Article> mapListDTOToEntity(List<ArticleDTO> articleDTOS) throws Exception {
        ArrayList<Article> articles = new ArrayList<>();
        for (ArticleDTO articleDTO : articleDTOS) {
            Article article = repository.getArticleById(articleDTO.getProductId());
            articles.add(article);
        }
        return articles;
    }

    public List<ArticleDTO> mapListEntityToDTO (List<Article> articles) {

        ArrayList<ArticleDTO> dtos = new ArrayList<>();

        for (Article article : articles) {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setProductId(article.getProductId());
            articleDTO.setName(article.getName());
            articleDTO.setBrand(article.getBrand());
            articleDTO.setQuantity(article.getQuantity());

            dtos.add(articleDTO);
        }
        return dtos;
    }

}