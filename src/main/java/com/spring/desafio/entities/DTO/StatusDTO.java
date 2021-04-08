package com.spring.desafio.entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusDTO {
    private Integer code;
    private String message;
}
