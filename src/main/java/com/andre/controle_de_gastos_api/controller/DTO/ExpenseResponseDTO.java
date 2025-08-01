package com.andre.controle_de_gastos_api.controller.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseResponseDTO(UUID id,
        String title,
        String description,
        BigDecimal amount,
        LocalDate date,
        String paymentMethod,
        String category) {

}
