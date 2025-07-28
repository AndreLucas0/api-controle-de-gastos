package com.andre.controle_de_gastos_api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andre.controle_de_gastos_api.controller.DTO.UpdateUserRoleDTO;
import com.andre.controle_de_gastos_api.controller.DTO.UserRoleUpdateResponseDTO;
import com.andre.controle_de_gastos_api.model.User;
import com.andre.controle_de_gastos_api.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        var users = userService.listUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") UUID userId) {
        var user = userService.findUserById(userId);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") UUID userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/roles")
    public ResponseEntity<?> updateUserRole(@PathVariable("userId") UUID userId, @RequestBody @Valid UpdateUserRoleDTO updateUserRoleDTO) {
        try {
            UserRoleUpdateResponseDTO response = userService.updateUserRole(userId, updateUserRoleDTO.role());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
        } else {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
    }
    
}
