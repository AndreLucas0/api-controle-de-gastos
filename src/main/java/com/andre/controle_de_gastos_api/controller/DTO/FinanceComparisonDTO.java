package com.andre.controle_de_gastos_api.controller.DTO;

import java.math.BigDecimal;

public record FinanceComparisonDTO(BigDecimal totalExpenses, BigDecimal totalIncomes, BigDecimal balance) {
    
}
