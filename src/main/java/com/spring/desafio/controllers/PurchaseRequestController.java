package com.spring.desafio.controllers;

import com.spring.desafio.entities.DTO.ArticlesDTO;
import com.spring.desafio.entities.DTO.PurchaseRequestDTO;
import com.spring.desafio.services.PurchaseRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchase-request")
public class PurchaseRequestController {

    PurchaseRequestService service = new PurchaseRequestService();

    @PostMapping
    public ResponseEntity<PurchaseRequestDTO> createPurchaseRequest (@RequestBody ArticlesDTO articlesFromRequest) throws Exception {
        PurchaseRequestDTO purchaseRequestDTO = new PurchaseRequestDTO();
        try {
            purchaseRequestDTO = service.processGenerateRequest(articlesFromRequest.getArticles());
            if (purchaseRequestDTO.getStatusCode().getCode().equals(400))
                return new ResponseEntity<>(purchaseRequestDTO,HttpStatus.BAD_REQUEST);
            if (purchaseRequestDTO.getStatusCode().getCode().equals(404))
                return new ResponseEntity<>(purchaseRequestDTO,HttpStatus.NOT_FOUND);
            if (purchaseRequestDTO.getStatusCode().getCode().equals(500))
                return new ResponseEntity<>(purchaseRequestDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e) {
            return new ResponseEntity<>(purchaseRequestDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(purchaseRequestDTO,HttpStatus.OK);
    }

}
