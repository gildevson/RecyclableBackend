package com.reciclaveis.reciclaveis.controller;

import com.reciclaveis.reciclaveis.dto.LoginDTO;
import com.reciclaveis.reciclaveis.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            // Validação de campos obrigatórios
            if (loginDTO.getEmail() == null || loginDTO.getEmail().isEmpty() ||
                    loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("Email e senha são obrigatórios.");
            }

            // Validar credenciais
            boolean isValid = authService.validateLogin(loginDTO.getEmail(), loginDTO.getPassword());

            if (isValid) {
                // Criar token JWT
                String token = Jwts.builder()
                        .setSubject(loginDTO.getEmail())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 dia
                        .signWith(SignatureAlgorithm.HS256, "chaveSecreta")
                        .compact();

                // Criar resposta
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login bem-sucedido");
                response.put("token", token);
                response.put("user", loginDTO.getEmail());

                return ResponseEntity.ok(response); // Retorna status 200 com os dados
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas"); // Retorna status 401
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro no servidor: " + e.getMessage());
        }
    }
}
