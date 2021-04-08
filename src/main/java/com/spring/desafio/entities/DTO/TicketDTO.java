package com.spring.desafio.entities.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.Random;

@Data
public class TicketDTO {
    private Integer id;
    private ArrayList<ArticleDTO> articles;
    private Double total;

    /*
    As i don`t have stored id in a database, i generate random ids as temp solution.
     */
    public void setId() {
        Random rand = new Random();
        int n = rand.nextInt(50);
        n += 1;
        this.id = n;
    }

}
