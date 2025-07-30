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

import com.andre.controle_de_gastos_api.controller.DTO.CreateExpenseDTO;
import com.andre.controle_de_gastos_api.controller.DTO.ExpenseResponseDTO;
import com.andre.controle_de_gastos_api.controller.DTO.UpdateExpenseDTO;
import com.andre.controle_de_gastos_api.model.Expense;
import com.andre.controle_de_gastos_api.model.User;
import com.andre.controle_de_gastos_api.service.ExpenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    
    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody CreateExpenseDTO createExpenseDTO) {
        var expenseId = expenseService.create(createExpenseDTO);

        return ResponseEntity.created(URI.create("/expenses/" + expenseId.toString())).build();
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> getExpensesByUserId(@AuthenticationPrincipal User user) {
        UUID userId = user.getId();
        var expenses = expenseService.getExpenses(userId);
        return ResponseEntity.ok(expenses);
    }


    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponseDTO> getExpenseById(@PathVariable("expenseId") UUID expenseId, @AuthenticationPrincipal User user) {
        UUID userId = user.getId();
        
        var expense = expenseService.getExpenseById(expenseId, userId);

        if (expense.isPresent()) {
            return ResponseEntity.ok(expense.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    } 

    @GetMapping("/month")
    public ResponseEntity<List<ExpenseResponseDTO>> getMonthlyExpenses(@AuthenticationPrincipal User user, @RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(expenseService.getExpensesByMonth(user.getId(), year, month));
    }


    @PutMapping("/{expenseId}")
    public ResponseEntity<Void> updateExpenseById(@PathVariable("expenseId") UUID expenseId, @RequestBody UpdateExpenseDTO updateExpenseDTO, @AuthenticationPrincipal User user) {
        UUID userId = user.getId();
        expenseService.updateExpenseById(expenseId, updateExpenseDTO, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpenseById(@PathVariable("expenseId") UUID expenseId, @AuthenticationPrincipal User user) {
        UUID userId = user.getId();
        expenseService.deleteById(expenseId, userId);
        return ResponseEntity.noContent().build();
    }
}
