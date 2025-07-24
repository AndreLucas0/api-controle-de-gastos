package com.andre.controle_de_gastos_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andre.controle_de_gastos_api.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, UUID>{
    
}
