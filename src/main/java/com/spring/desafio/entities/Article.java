package com.spring.desafio.entities;

import lombok.Data;

@Data
public class Article {
    private String productId;
    private String name;
    private String category;
    private String brand;
    private Double price;
    private Integer quantity;
    private Boolean freeShipping;
    private Integer prestige;
}
