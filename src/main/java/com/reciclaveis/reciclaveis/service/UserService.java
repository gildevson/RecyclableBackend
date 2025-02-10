package com.reciclaveis.reciclaveis.service;

import com.reciclaveis.reciclaveis.entity.User;
import com.reciclaveis.reciclaveis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void registerUser(String email, String rawPassword, String name) {
        // Validações básicas
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("O email é obrigatório.");
        }
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }

        // Verifica se o email já está cadastrado
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }

        // Gera o hash da senha
        String hashedPassword = encoder.encode(rawPassword);
        logger.info("Hash gerado para a senha: {}", hashedPassword);

        // Cria e salva o usuário
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(hashedPassword);
        newUser.setName(name);
        userRepository.save(newUser);

        logger.info("Usuário {} registrado com sucesso.", email);
    }
}