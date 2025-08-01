package com.andre.controle_de_gastos_api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "incomes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Income {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank(message = "O título é obrigatório")
    private String title;
    private String description;
    @NotNull
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
    private BigDecimal amount;
    @NotNull(message = "A data é obrigatória")
    private LocalDate date;
    @NotBlank
    private String source;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
