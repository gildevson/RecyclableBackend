package com.reciclaveis.reciclaveis.controller;

import com.reciclaveis.reciclaveis.dto.AuthResponseDTO;
import com.reciclaveis.reciclaveis.dto.ForgotPasswordRequestDTO;
import com.reciclaveis.reciclaveis.dto.LoginDTO;
import com.reciclaveis.reciclaveis.dto.ResetPasswordDTO;
import com.reciclaveis.reciclaveis.entity.User;
import com.reciclaveis.reciclaveis.service.AuthService;
import com.reciclaveis.reciclaveis.service.EmailService;
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
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private AuthService authService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        if (loginDTO.getEmail() == null || loginDTO.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("O e-mail é obrigatório.");
        }

        if (loginDTO.getPassword() == null || loginDTO.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body("A senha é obrigatória.");
        }

        boolean isValid = authService.validateLogin(loginDTO.getEmail(), loginDTO.getPassword());
        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }

        User user = authService.getUserByEmail(loginDTO.getEmail());

        if (jwtSecret.length() < 32) {
            return ResponseEntity.badRequest().body("A chave JWT precisa ter no mínimo 32 caracteres.");
        }

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 horas
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        var permissions = user.getPermissions().stream()
                .map(p -> new AuthResponseDTO.PermissionDTO(p.getId(), p.getDescription()))
                .toList();

        var response = new AuthResponseDTO(token, user.getId(), user.getName(), user.getEmail(), permissions);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequestDTO body) {
        String email = body.getEmail();

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body("O e-mail é obrigatório.");
        }

        try {
            User user = authService.getUserByEmail(email);

            Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            String token = Jwts.builder()
                    .setSubject(user.getEmail())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 30 minutos
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

            String resetLink = "http://localhost:3000/reset-password?token=" + token;

            String assunto = "🔐 Recuperação de Senha";
            String mensagem = String.format("""
                    Olá %s,

                    Recebemos sua solicitação de redefinição de senha.

                    Clique no link abaixo para criar uma nova senha:
                    %s

                    Se você não solicitou essa ação, ignore este e-mail.

                    Atenciosamente,
                    Equipe de Suporte
                    """, user.getName(), resetLink);

            emailService.sendEmail(email, assunto, mensagem);

            return ResponseEntity.ok("E-mail com link de redefinição enviado com sucesso.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("E-mail não encontrado.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar solicitação.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO body) {
        String token = body.getToken();
        String password = body.getPassword();
        String confirmPassword = body.getConfirmPassword();

        if (token == null || token.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body("Token e nova senha são obrigatórios.");
        }

        if (!password.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("As senhas não coincidem.");
        }

        try {
            String email = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            User user = authService.getUserByEmail(email);
            authService.updatePassword(user.getId(), password);

            return ResponseEntity.ok("Senha redefinida com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        }
    }
}
