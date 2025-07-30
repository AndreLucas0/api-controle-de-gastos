package com.andre.controle_de_gastos_api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andre.controle_de_gastos_api.controller.DTO.FinanceComparisonDTO;
import com.andre.controle_de_gastos_api.model.Expense;
import com.andre.controle_de_gastos_api.model.Income;
import com.andre.controle_de_gastos_api.repository.ExpenseRepository;
import com.andre.controle_de_gastos_api.repository.IncomeRepository;

@Service
public class FinanceService {
    
    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    IncomeRepository incomeRepository;

    public FinanceComparisonDTO compareExpensesAndIncomes(UUID userId, Integer year, Integer month) {
        LocalDate start;
        LocalDate end;

        if (year != null && month != null) {
            start = LocalDate.of(year, month, 1);
            end = start.withDayOfMonth(start.lengthOfMonth());
        } else if (year != null) {
            start = LocalDate.of(year, 1, 1);
            end = LocalDate.of(year, 12, 31);
        } else {
            start = LocalDate.of(2025, 1, 1); // Data antiga
            end = LocalDate.now();
        }

        BigDecimal totalExpenses = expenseRepository
                .findByUserIdAndDateBetween(userId, start, end)
                .stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalIncomes = incomeRepository
                .findByUserIdAndDateBetween(userId, start, end)
                .stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal balance = totalIncomes.subtract(totalExpenses);

        return new FinanceComparisonDTO(totalExpenses, totalIncomes, balance);
    }
}
