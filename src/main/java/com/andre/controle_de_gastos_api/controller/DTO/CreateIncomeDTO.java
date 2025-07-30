package com.andre.controle_de_gastos_api.controller.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateIncomeDTO(@NotBlank String title, String description, @NotNull BigDecimal amount, @NotNull LocalDate date, @NotBlank String source) {
    
}
