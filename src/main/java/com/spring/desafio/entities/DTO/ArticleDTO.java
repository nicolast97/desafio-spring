package com.spring.desafio.entities.DTO;

import lombok.Data;

@Data
public class ArticleDTO {
    private String productId;
    private String name;
    private String brand;
    private Integer quantity;
}
