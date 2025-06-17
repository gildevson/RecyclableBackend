package com.reciclaveis.reciclaveis.util;

import com.reciclaveis.reciclaveis.dto.LoginDTO;
import com.reciclaveis.reciclaveis.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthValidator {

    @Autowired
    private AuthService authService;

    // Remova ou altere o endpoint duplicado
    @PostMapping("/validate")
    public ResponseEntity<?> validateOnly(@RequestBody LoginDTO loginDTO) {
        if (loginDTO.getEmail() == null || loginDTO.getEmail().isEmpty() || loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Email e senha são obrigatórios.");
        }

        boolean isValid = authService.validateLogin(loginDTO.getEmail(), loginDTO.getPassword());
        if (isValid) {
            return ResponseEntity.ok("Validação bem-sucedida!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }
    }
}


