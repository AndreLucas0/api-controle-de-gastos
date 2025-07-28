package com.andre.controle_de_gastos_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andre.controle_de_gastos_api.controller.DTO.AuthenticationDTO;
import com.andre.controle_de_gastos_api.controller.DTO.LoginResponseDTO;
import com.andre.controle_de_gastos_api.controller.DTO.RegisterDTO;
import com.andre.controle_de_gastos_api.infra.security.TokenService;
import com.andre.controle_de_gastos_api.model.User;
import com.andre.controle_de_gastos_api.model.UserRole;
import com.andre.controle_de_gastos_api.repository.UserRepository;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO authenticationDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDto.login(), authenticationDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        User user = this.userRepository.findUserByLogin(authenticationDto.login());

        return ResponseEntity.ok(new LoginResponseDTO(user.getName(), token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO registerDto) {
        if (this.userRepository.findUserByLogin(registerDto.login()) != null) {
            return ResponseEntity.badRequest().build();
        } else {
            String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());
            User newUser = new User(registerDto.name(), registerDto.login(), encryptedPassword, UserRole.USER);

            userRepository.save(newUser);

            return ResponseEntity.ok().build();
        }
    }

}
