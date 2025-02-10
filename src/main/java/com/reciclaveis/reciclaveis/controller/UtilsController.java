
package com.reciclaveis.reciclaveis.controller;

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

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/generate-hash")
    public ResponseEntity<String> generateHash(@RequestBody Map<String, String> request) {
        String rawPassword = request.get("password");
        if (rawPassword == null || rawPassword.isEmpty()) {
            return ResponseEntity.badRequest().body("A senha é obrigatória.");
        }

        String hashedPassword = encoder.encode(rawPassword);
        return ResponseEntity.ok(hashedPassword);
    }
}
