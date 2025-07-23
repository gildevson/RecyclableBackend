package com.reciclaveis.reciclaveis.controller;

import com.reciclaveis.reciclaveis.dto.ForgotPasswordRequestDTO;
import com.reciclaveis.reciclaveis.dto.ResetPasswordDTO;
import com.reciclaveis.reciclaveis.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequestDTO dto) {
        passwordResetService.criarTokenDeRedefinicao(dto.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordDTO dto) {
        passwordResetService.redefinirSenha(dto.getToken(), dto.getNewPassword());
        return ResponseEntity.ok().build();
    }
}
