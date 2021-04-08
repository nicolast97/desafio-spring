package com.spring.desafio.services;

import com.spring.desafio.entities.Article;
import com.spring.desafio.entities.DTO.ArticleDTO;
import com.spring.desafio.entities.DTO.PurchaseRequestDTO;
import com.spring.desafio.entities.DTO.StatusDTO;
import com.spring.desafio.entities.DTO.TicketDTO;
import com.spring.desafio.exceptions.QuantityException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PurchaseRequestService {

    ArticleService articleService = new ArticleService();

    public PurchaseRequestDTO processGenerateRequest (List<ArticleDTO> articlesDTOS) throws Exception {
        PurchaseRequestDTO requestDTO = new PurchaseRequestDTO();
        TicketDTO ticket = new TicketDTO();
        StatusDTO status;
        try{
            ArrayList<Article> articlesFromDataBase = (ArrayList<Article>) articleService.mapListDTOToEntity(articlesDTOS);
            ticket = generateTicketFromPurchaseOrder(articlesFromDataBase,articlesDTOS);
            status = new StatusDTO(200, "The request has been processed successfully");
        }
        catch (QuantityException e) {
            ticket.setArticles((ArrayList<ArticleDTO>) articlesDTOS);
            status = new StatusDTO(400,e.getMessage());
        }
        catch (NoSuchElementException e) {
            ticket.setArticles((ArrayList<ArticleDTO>) articlesDTOS);
            status = new StatusDTO(404,e.getMessage());
        }
        catch (RuntimeException e) {
            ticket.setArticles((ArrayList<ArticleDTO>) articlesDTOS);
            status = new StatusDTO(500, e.getMessage());
        }
        requestDTO.setTicket(ticket);
        requestDTO.setStatusCode(status);
        return requestDTO;
    }

    private TicketDTO generateTicketFromPurchaseOrder (List<Article> articles, List<ArticleDTO> articlesDTOS) throws QuantityException {
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
