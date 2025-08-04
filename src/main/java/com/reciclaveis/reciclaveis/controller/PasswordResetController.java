package com.reciclaveis.reciclaveis.controller;

import com.reciclaveis.reciclaveis.dto.ForgotPasswordRequestDTO;
import com.reciclaveis.reciclaveis.dto.ResetPasswordDTO;
import com.reciclaveis.reciclaveis.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/forgot")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequestDTO dto) {
        passwordResetService.criarTokenDeRedefinicao(dto.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordDTO dto) {
        if (dto.getPassword() == null || dto.getConfirmPassword() == null || !dto.getPassword().equals(dto.getConfirmPassword())) {
            return ResponseEntity.badRequest().build();
        }

        passwordResetService.redefinirSenha(dto.getToken(), dto.getPassword());
        return ResponseEntity.ok().build();
    }
}
