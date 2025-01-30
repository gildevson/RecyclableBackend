package com.reciclaveis.reciclaveis.controller;

import com.reciclaveis.reciclaveis.dto.LoginDTO;
import com.reciclaveis.reciclaveis.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            if (loginDTO.getEmail() == null || loginDTO.getEmail().isEmpty() ||
                    loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("Email e senha são obrigatórios.");
            }

            boolean isValid = authService.validateLogin(loginDTO.getEmail(), loginDTO.getPassword());
            if (isValid) {
                String token = Jwts.builder()
                        .setSubject(loginDTO.getEmail())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 dia
                        .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes())
                        .compact();

                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login bem-sucedido");
                response.put("token", token);
                response.put("user", loginDTO.getEmail());

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Mostra o stack trace no log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro no servidor: " + e.getMessage());
        }
    }

}
