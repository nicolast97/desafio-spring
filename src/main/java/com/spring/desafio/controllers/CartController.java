package com.spring.desafio.controllers;

import com.spring.desafio.entities.DTO.CartDTO;
import com.spring.desafio.entities.DTO.PurchaseRequestDTO;
import com.spring.desafio.services.ArticleService;
import com.spring.desafio.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    ArticleService articleService = new ArticleService();
    CartService cartService = new CartService();

    @PostMapping
    public ResponseEntity<CartDTO> createCart (@RequestBody ArrayList<PurchaseRequestDTO> requests) throws Exception {
        CartDTO cart = cartService.createCart(requests);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

}
