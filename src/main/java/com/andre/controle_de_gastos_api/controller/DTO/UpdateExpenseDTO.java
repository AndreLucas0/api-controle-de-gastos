package com.andre.controle_de_gastos_api.controller.DTO;

import java.math.BigDecimal;


public record UpdateExpenseDTO(String title, String description, BigDecimal amount, String date, String paymentMethod, String category) {
    
}
