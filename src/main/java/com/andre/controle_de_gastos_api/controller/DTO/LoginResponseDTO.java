package com.andre.controle_de_gastos_api.controller.DTO;

import jakarta.validation.constraints.NotBlank;

public record LoginResponseDTO(@NotBlank String name,@NotBlank String token) {
    
}
