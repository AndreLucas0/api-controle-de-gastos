package com.andre.controle_de_gastos_api.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andre.controle_de_gastos_api.controller.DTO.UserRoleUpdateResponseDTO;
import com.andre.controle_de_gastos_api.model.User;
import com.andre.controle_de_gastos_api.model.UserRole;
import com.andre.controle_de_gastos_api.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(UUID userId){
        return userRepository.findById(userId);
    }

    public void deleteById(UUID userId) {
        var userExists = userRepository.existsById(userId);

        if (userExists) {
            userRepository.deleteById(userId);
        }
    }

    public UserRoleUpdateResponseDTO updateUserRole(UUID userId, UserRole newRole) {
        var user = userRepository.findById(userId);
        
        if (user.isPresent()) {
            var userToUpdate = user.get();
            UserRole oldRole = userToUpdate.getRole();
            
            if (oldRole == newRole) {
                throw new RuntimeException("O usuário já possui a role " + newRole);
            }
            
            userToUpdate.setRole(newRole);
            userRepository.save(userToUpdate);
            
            return new UserRoleUpdateResponseDTO(
                "Role atualizada com sucesso",
                userId.toString(),
                oldRole,
                newRole,
                userToUpdate.getLogin()
            );
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }
}
