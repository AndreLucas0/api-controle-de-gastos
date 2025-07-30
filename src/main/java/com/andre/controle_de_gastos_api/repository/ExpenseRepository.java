package com.andre.controle_de_gastos_api.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andre.controle_de_gastos_api.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, UUID>{
    
    List<Expense> findByUserId(UUID userId);

    Optional<Expense> findByIdAndUserId(UUID expenseId, UUID userId);

    List<Expense> findByUserIdAndDateBetween(UUID userId, LocalDate startDate, LocalDate endDate);
}
