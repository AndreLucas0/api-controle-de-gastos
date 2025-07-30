package com.andre.controle_de_gastos_api.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andre.controle_de_gastos_api.model.Income;

public interface IncomeRepository extends JpaRepository<Income, UUID>{

    List<Income> findByUserId(UUID userId);

    Optional<Income> findByIdAndUserId(UUID incomeId, UUID userId);

    List<Income> findByUserIdAndDateBetween(UUID userId, LocalDate startDate, LocalDate endDate);
}
