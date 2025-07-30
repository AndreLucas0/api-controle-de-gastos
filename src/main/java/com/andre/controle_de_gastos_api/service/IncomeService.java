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

import com.andre.controle_de_gastos_api.controller.DTO.CreateIncomeDTO;
import com.andre.controle_de_gastos_api.controller.DTO.IncomeResponseDTO;
import com.andre.controle_de_gastos_api.controller.DTO.UpdateIncomeDTO;
import com.andre.controle_de_gastos_api.model.Income;
import com.andre.controle_de_gastos_api.model.User;
import com.andre.controle_de_gastos_api.repository.IncomeRepository;
import com.andre.controle_de_gastos_api.repository.UserRepository;

@Service
public class IncomeService {
    
    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserRepository userRepository;

    public UUID create(CreateIncomeDTO createIncomeDTO) {

        //DTO -> Entity
        Income income = new Income();
        income.setTitle(createIncomeDTO.title());
        income.setAmount(createIncomeDTO.amount());
        income.setDate(createIncomeDTO.date());
        income.setDescription(createIncomeDTO.description());
        income.setSource(createIncomeDTO.source());

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByLogin(email);
        income.setUser(user);

        var savedIncome = incomeRepository.save(income);

        return savedIncome.getId();

    }

    public List<IncomeResponseDTO> getIncomes(UUID userId) {
        return incomeRepository.findByUserId(userId).stream()
            .map(income -> new IncomeResponseDTO(
                income.getId(),
                income.getTitle(),
                income.getDescription(),
                income.getAmount(),
                income.getDate(),
                income.getSource()
            ))
            .toList();
    }

    public List<IncomeResponseDTO> getIncomesByMonth(UUID userId, int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return incomeRepository.findByUserIdAndDateBetween(userId, start, end)
                .stream()
                .map(income -> new IncomeResponseDTO(
                        income.getId(),
                        income.getTitle(),
                        income.getDescription(),
                        income.getAmount(),
                        income.getDate(),
                        income.getSource()
                ))
                .toList();
    }

    public void updateIncomeById(UUID incomeId, UpdateIncomeDTO updateIncomeDTO, UUID userId) {
        var income = incomeRepository.findByIdAndUserId(incomeId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Despesa não encontrada"));;


        if (updateIncomeDTO.title() != null) {
            income.setTitle(updateIncomeDTO.title());
        } if (updateIncomeDTO.description() != null) {
            income.setDescription(updateIncomeDTO.description());
        } if (updateIncomeDTO.amount() != null) {
            income.setAmount(updateIncomeDTO.amount());
        } if (updateIncomeDTO.date() != null) {
            income.setDate(updateIncomeDTO.date());
        } if (updateIncomeDTO.source() != null) {
            income.setSource(updateIncomeDTO.source());
        } 

        incomeRepository.save(income);
        
    }

    public Optional<IncomeResponseDTO> getIncomeById(UUID incomeId,UUID userId) {
        return incomeRepository.findByIdAndUserId(incomeId, userId)
            .map(income -> new IncomeResponseDTO(
                income.getId(),
                income.getTitle(),
                income.getDescription(),
                income.getAmount(),
                income.getDate(),
                income.getSource()
            ));
    }

    public void delete(UUID expenseId, UUID userId) {
        var income = incomeRepository.findByIdAndUserId(expenseId, userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Renda não encontrada"));
    
        incomeRepository.delete(income);
    }

    
}
