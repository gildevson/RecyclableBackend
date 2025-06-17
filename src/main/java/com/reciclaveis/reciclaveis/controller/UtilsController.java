package com.reciclaveis.reciclaveis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/utils")
public class UtilsController {

    private static final Logger log = LoggerFactory.getLogger(UtilsController.class);

    private final BCryptPasswordEncoder encoder;

    public UtilsController(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @PostMapping("/generate-hash")
    public ResponseEntity<Map<String, String>> generateHash(@RequestBody Map<String, String> request) {
        String rawPassword = request.get("password");

        if (rawPassword == null || rawPassword.isEmpty()) {
            log.error("Tentativa de gerar hash com senha nula ou vazia.");
            return ResponseEntity.badRequest().body(Map.of("error", "A senha é obrigatória."));
        }

        String hashedPassword = encoder.encode(rawPassword);
        log.info("Hash gerado com sucesso.");
        return ResponseEntity.ok(Map.of("hashedPassword", hashedPassword));
    }
}
