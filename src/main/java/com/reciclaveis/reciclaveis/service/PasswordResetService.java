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
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    public void criarTokenDeRedefinicao(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Cria token
        String token = UUID.randomUUID().toString();

        // Salva no banco
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
        tokenRepository.save(resetToken);

        // Gera link
        String resetLink = "http://localhost:3000/reset-password?token=" + token;

        // Corpo do e-mail
        String corpo = "Olá,\n\nClique no link abaixo para redefinir sua senha:\n"
                + resetLink + "\n\nEste link é válido por 1 hora.";

        // Envia o e-mail
        emailService.sendEmail(email, "Redefinição de Senha", corpo);
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
