package com.spring.desafio.entities.DTO;

import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    List<ArticleDTO> articlesDTOS;
    double totalPrice;
}
