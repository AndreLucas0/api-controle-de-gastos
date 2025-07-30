package com.andre.controle_de_gastos_api.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andre.controle_de_gastos_api.controller.DTO.FinanceComparisonDTO;
import com.andre.controle_de_gastos_api.model.User;
import com.andre.controle_de_gastos_api.service.FinanceService;

@RestController
@RequestMapping("/finances")
public class FinanceController {
    
    @Autowired
    FinanceService financeService;

    @GetMapping("/compare")
    public ResponseEntity<FinanceComparisonDTO> compareFinance(@AuthenticationPrincipal User user, @RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
        
        UUID userId = user.getId();

        var finance = financeService.compareExpensesAndIncomes(userId, year, month);

        return ResponseEntity.ok(finance);
    }
}
