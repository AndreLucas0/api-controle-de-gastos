package com.andre.controle_de_gastos_api.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andre.controle_de_gastos_api.controller.DTO.CreateIncomeDTO;
import com.andre.controle_de_gastos_api.controller.DTO.IncomeResponseDTO;
import com.andre.controle_de_gastos_api.controller.DTO.UpdateIncomeDTO;
import com.andre.controle_de_gastos_api.model.Income;
import com.andre.controle_de_gastos_api.model.User;
import com.andre.controle_de_gastos_api.service.IncomeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/incomes")
public class IncomeController {
    
    @Autowired
    private IncomeService incomeService;

    @PostMapping
    public ResponseEntity<Income> createIncome(@Valid @RequestBody CreateIncomeDTO createIncomeDTO) {
        var incomeId = incomeService.create(createIncomeDTO);

        return ResponseEntity.created(URI.create("/incomes/" + incomeId.toString())).build();
    }

    @GetMapping
    public ResponseEntity<List<IncomeResponseDTO>> getIncomesByUserId(@AuthenticationPrincipal User user) {
        UUID userId = user.getId();
        var incomes = incomeService.getIncomes(userId);
        return ResponseEntity.ok(incomes);
    }

    @GetMapping("/{incomeId}")
    public ResponseEntity<IncomeResponseDTO> getIncomeById(@PathVariable("incomeId") UUID incomeId, @AuthenticationPrincipal User user) {
        UUID userId = user.getId();

        var income = incomeService.getIncomeById(incomeId, userId);

        if (income.isPresent()) {
            return ResponseEntity.ok(income.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/month")
    public ResponseEntity<List<IncomeResponseDTO>> getMonthlyIncomes(@AuthenticationPrincipal User user, @RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(incomeService.getIncomesByMonth(user.getId(), year, month));
    }

    @PutMapping("/{incomeId}")
    public ResponseEntity<Void> updateIncomeById(@PathVariable("incomeId") UUID incomeId, @RequestBody UpdateIncomeDTO updateIncomeDTO, @AuthenticationPrincipal User user) {
        UUID userId = user.getId();
        incomeService.updateIncomeById(incomeId, updateIncomeDTO, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{incomeId}")
    public ResponseEntity<Void> deleteIncomeById(@PathVariable("incomeId") UUID incomeId, @AuthenticationPrincipal User user) {
        UUID userId = user.getId();
        incomeService.delete(incomeId, userId);
        return ResponseEntity.noContent().build();
    }
}
