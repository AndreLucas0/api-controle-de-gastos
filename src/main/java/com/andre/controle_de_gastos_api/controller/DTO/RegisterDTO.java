package com.andre.controle_de_gastos_api.controller.DTO;

import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(@NotBlank String name,@NotBlank String login,@NotBlank String password) {
    
}
