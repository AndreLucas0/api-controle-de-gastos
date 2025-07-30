package com.andre.controle_de_gastos_api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.andre.controle_de_gastos_api.controller.DTO.CreateExpenseDTO;
import com.andre.controle_de_gastos_api.controller.DTO.ExpenseResponseDTO;
import com.andre.controle_de_gastos_api.controller.DTO.UpdateExpenseDTO;
import com.andre.controle_de_gastos_api.model.Expense;
import com.andre.controle_de_gastos_api.model.User;
import com.andre.controle_de_gastos_api.repository.ExpenseRepository;
import com.andre.controle_de_gastos_api.repository.UserRepository;


@Service
public class ExpenseService {
    
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public UUID create(CreateExpenseDTO createExpenseDTO) {

        //DTO -> Entity
        Expense expense = new Expense();
        expense.setTitle(createExpenseDTO.title());
        expense.setDescription(createExpenseDTO.description());
        expense.setAmount(createExpenseDTO.amount());
        expense.setDate(createExpenseDTO.date());
        expense.setPaymentMethod(createExpenseDTO.paymentMethod());
        expense.setCategory(createExpenseDTO.category());

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByLogin(email);
        expense.setUser(user);

        var savedExpense = expenseRepository.save(expense);

        return savedExpense.getId();
    }

    public List<ExpenseResponseDTO> getExpenses(UUID userId) {
        return expenseRepository.findByUserId(userId).stream()
            .map(expense -> new ExpenseResponseDTO(
                expense.getId(),
                expense.getTitle(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getDate(),
                expense.getPaymentMethod(),
                expense.getCategory()
            ))
            .toList();
    }

    public List<ExpenseResponseDTO> getExpensesByMonth(UUID userId, int year, int month) {
    LocalDate start = LocalDate.of(year, month, 1);
    LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
    
    return expenseRepository.findByUserIdAndDateBetween(userId, start, end)
            .stream()
            .map(expense -> new ExpenseResponseDTO(
                    expense.getId(),
                    expense.getTitle(),
                    expense.getDescription(),
                    expense.getAmount(),
                    expense.getDate(),
                    expense.getPaymentMethod(),
                    expense.getCategory()
            ))
            .toList();
    }

    public void updateExpenseById(UUID expenseId, UpdateExpenseDTO updateExpenseDTO, UUID userId) {
        var expense = expenseRepository.findByIdAndUserId(expenseId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Despesa não encontrada"));;


        if (updateExpenseDTO.title() != null) {
            expense.setTitle(updateExpenseDTO.title());
        } if (updateExpenseDTO.description() != null) {
            expense.setDescription(updateExpenseDTO.description());
        } if (updateExpenseDTO.amount() != null) {
            expense.setAmount(updateExpenseDTO.amount());
        } if (updateExpenseDTO.date() != null) {
            expense.setDate(updateExpenseDTO.date());
        } if (updateExpenseDTO.paymentMethod() != null) {
            expense.setPaymentMethod(updateExpenseDTO.paymentMethod());
        } if (updateExpenseDTO.category() != null) {
            expense.setCategory(updateExpenseDTO.category());
        }

        expenseRepository.save(expense);
        
    }

    public Optional<ExpenseResponseDTO> getExpenseById(UUID expenseId, UUID userId) {
        return expenseRepository.findByIdAndUserId(expenseId, userId)
                        .map(expense -> new ExpenseResponseDTO(
                            expense.getId(),
                            expense.getTitle(),
                            expense.getDescription(),
                            expense.getAmount(),
                            expense.getDate(),
                            expense.getPaymentMethod(),
                            expense.getCategory()
                        ));
    }

    public void deleteById(UUID expenseId, UUID userId) {
        var expense = expenseRepository.findByIdAndUserId(expenseId, userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Despesa não encontrada"));

        expenseRepository.delete(expense);    
    }
}
