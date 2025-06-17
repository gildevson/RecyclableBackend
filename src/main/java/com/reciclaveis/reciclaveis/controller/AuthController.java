package com.reciclaveis.reciclaveis.controller;

import com.reciclaveis.reciclaveis.dto.LoginDTO;
import com.reciclaveis.reciclaveis.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Key;
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
            // Validação de campos obrigatórios
            if (loginDTO.getEmail() == null || loginDTO.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body("O email é obrigatório.");
            }
            if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("A senha é obrigatória.");
            }

            // Validação do login
            boolean isValid = authService.validateLogin(loginDTO.getEmail(), loginDTO.getPassword());
            if (isValid) {
                // Supondo que o nome do usuário possa ser obtido do AuthService
                String name = authService.getUserNameByEmail(loginDTO.getEmail()); // Ajuste conforme necessário

                if (jwtSecret.length() < 32) {
                    throw new IllegalArgumentException("A chave do JWT deve ter pelo menos 256 bits (32 caracteres).");
                }
                byte[] keyBytes = jwtSecret.getBytes();
                Key key = Keys.hmacShaKeyFor(keyBytes);

                // Geração do token JWT
                String token = Jwts.builder()
                        .setSubject(loginDTO.getEmail())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Token válido por 1 dia
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact();

                // Construção da resposta
                Map<String, Object> response = new HashMap<>();
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Login bem-sucedido");
                response.put("token", token);
                response.put("name", name);  // Agora a variável name está definida corretamente
                response.put("user", loginDTO.getEmail());

                System.out.println("JWT Secret: " + jwtSecret);

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro no token JWT: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro durante o login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor.");
        }
    }
}