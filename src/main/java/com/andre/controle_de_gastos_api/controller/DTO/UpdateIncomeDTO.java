package com.andre.controle_de_gastos_api.controller.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateIncomeDTO(String title, String description, BigDecimal amount, LocalDate date, String source) {
    
}
