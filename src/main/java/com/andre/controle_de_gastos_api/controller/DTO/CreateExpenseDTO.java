package com.andre.controle_de_gastos_api.controller.DTO;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateExpenseDTO(@NotBlank String title, String description, @NotNull BigDecimal amount, @NotBlank String date, @NotBlank String paymentMethod, @NotBlank String category) {
    
}
