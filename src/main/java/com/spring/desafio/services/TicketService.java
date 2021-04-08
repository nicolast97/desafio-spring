package com.spring.desafio.services;

import com.spring.desafio.entities.Article;
import com.spring.desafio.entities.DTO.ArticleDTO;
import com.spring.desafio.entities.DTO.TicketDTO;
import com.spring.desafio.exceptions.QuantityException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    public TicketDTO generateTicketFromPurchaseOrder (List<Article> articles, List<ArticleDTO> articlesDTOS) throws QuantityException {
        TicketDTO ticket = new TicketDTO();
        ticket.setId();
        Double totalPrice = 0.0;
        ticket.setArticles((ArrayList<ArticleDTO>) articlesDTOS);
        for (int i = 0; i < articles.size(); i++ ){
            if (articles.get(i).getQuantity() <= articlesDTOS.get(i).getQuantity()){
                throw new QuantityException("Quantity can not be processed for product " + articlesDTOS.get(i).getName());
            }
            totalPrice += articles.get(i).getPrice();
        }
        ticket.setTotal(totalPrice);
        return ticket;
    }

}
