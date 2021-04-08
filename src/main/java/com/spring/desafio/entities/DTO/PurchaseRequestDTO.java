package com.spring.desafio.entities.DTO;

import lombok.Data;

@Data
public class PurchaseRequestDTO{
    private TicketDTO ticket;
    private StatusDTO statusCode;

}
