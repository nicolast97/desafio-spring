package com.spring.desafio.services;

import com.spring.desafio.entities.Article;
import com.spring.desafio.entities.DTO.CartDTO;
import com.spring.desafio.entities.DTO.PurchaseRequestDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartService {

    ArticleService articleService = new ArticleService();

    public CartDTO createCart(ArrayList<PurchaseRequestDTO> requests) throws Exception {
        CartDTO cart = new CartDTO();
        Double totalPrice = 0.0;
        for (PurchaseRequestDTO request : requests) {
            if (request.getStatusCode().getCode().equals(200)){
                cart.setArticlesDTOS(request.getTicket().getArticles());
            }
            ArrayList<Article> articles = (ArrayList<Article>) articleService.mapListDTOToEntity(request.getTicket().getArticles());
            for (Article article : articles) {
                totalPrice += article.getPrice();
            }
        }
        cart.setTotalPrice(totalPrice);
        return cart;
    }

}
