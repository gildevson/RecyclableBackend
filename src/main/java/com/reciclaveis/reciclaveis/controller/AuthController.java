package com.reciclaveis.reciclaveis.controller;

import com.reciclaveis.reciclaveis.dto.AuthResponseDTO;
import com.reciclaveis.reciclaveis.dto.LoginDTO;
import com.reciclaveis.reciclaveis.entity.User;
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
            if (loginDTO.getEmail() == null || loginDTO.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body("O email é obrigatório.");
            }
            if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("A senha é obrigatória.");
            }

            boolean isValid = authService.validateLogin(loginDTO.getEmail(), loginDTO.getPassword());
            if (!isValid) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
            }

            User user = authService.getUserByEmail(loginDTO.getEmail());

            if (jwtSecret.length() < 32) {
                throw new IllegalArgumentException("A chave do JWT deve ter pelo menos 256 bits (32 caracteres).");
            }

            Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

            String token = Jwts.builder()
                    .setSubject(user.getEmail())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 horas
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

            // Mapeia as permissões do usuário para DTO
            var permissionDTOs = user.getPermissions().stream()
                    .map(p -> new AuthResponseDTO.PermissionDTO(p.getId(), p.getDescription()))
                    .toList();

            // Cria e retorna o DTO completo
            var response = new AuthResponseDTO(
                    token,
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    permissionDTOs
            );

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro na configuração do JWT: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro durante o login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor.");
        }
    }
}