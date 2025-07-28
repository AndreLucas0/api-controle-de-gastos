package com.andre.controle_de_gastos_api.controller.DTO;

import com.andre.controle_de_gastos_api.model.UserRole;

public record UserRoleUpdateResponseDTO(String message, String userId, UserRole oldRole, UserRole newRole, String userLogin) {
    
}
