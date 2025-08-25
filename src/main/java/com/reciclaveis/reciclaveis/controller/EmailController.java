package com.reciclaveis.reciclaveis.controller;

import com.reciclaveis.reciclaveis.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class EmailController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/verifica-email")
    public ResponseEntity<?> verificaEmail(@RequestParam String email) {
        boolean existe = usuarioRepository.existsByEmail(email);

        if (existe) {
            return ResponseEntity.ok("e-email encontrado.");
        } else {
            return ResponseEntity.status(404).body("E-email não encontrado");
        }
    }
}
