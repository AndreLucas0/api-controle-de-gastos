package com.andre.controle_de_gastos_api.controller.DTO;

import com.andre.controle_de_gastos_api.model.UserRole;

import jakarta.validation.constraints.NotNull;

public record UpdateUserRoleDTO(@NotNull UserRole role) {
    
}
