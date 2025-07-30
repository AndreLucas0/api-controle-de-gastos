package com.andre.controle_de_gastos_api.controller.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;


public record UpdateExpenseDTO(String title, String description, BigDecimal amount, LocalDate date, String paymentMethod, String category) {
    
}
