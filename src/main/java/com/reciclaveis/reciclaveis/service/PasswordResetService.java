package com.reciclaveis.reciclaveis.service;

import com.reciclaveis.reciclaveis.entity.PasswordResetToken;
import com.reciclaveis.reciclaveis.entity.User;
import com.reciclaveis.reciclaveis.repository.PasswordResetTokenRepository;
import com.reciclaveis.reciclaveis.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    // 1. Gerar token e enviar email
    @Transactional
    public void criarTokenDeRedefinicao(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado para o email: " + email);
        }

        User user = optionalUser.get();

        String token = UUID.randomUUID().toString();
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(15);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpirationDate(expiration);

        tokenRepository.save(resetToken);

        String link = "http://localhost:3000/reset-password?token=" + token;
        emailService.sendEmail(user.getEmail(), "Redefinição de senha",
                "Clique no link para redefinir sua senha: " + link);
    }

    // 2. Validar token recebido
    public boolean validarToken(String token) {
        Optional<PasswordResetToken> optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isEmpty()) return false;

        PasswordResetToken resetToken = optionalToken.get();
        return resetToken.getExpirationDate().isAfter(LocalDateTime.now());
    }

    // 3. Atualizar senha
    @Transactional
    public void redefinirSenha(String token, String novaSenha) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido."));

        if (resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expirado.");
        }

        User user = resetToken.getUser();
        user.setPassword(new BCryptPasswordEncoder().encode(novaSenha));
        userRepository.save(user);

        tokenRepository.delete(resetToken); // Token usado é descartado
    }
}
