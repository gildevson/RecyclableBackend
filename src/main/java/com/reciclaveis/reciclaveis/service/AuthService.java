package com.reciclaveis.reciclaveis.service;

import com.reciclaveis.reciclaveis.entity.User;
import com.reciclaveis.reciclaveis.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean validateLogin(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        boolean isMatch = passwordEncoder.matches(password, user.getPassword());

        // Logs para depuração
        System.out.println("Email fornecido: " + email);
        System.out.println("Senha fornecida: " + password);
        System.out.println("Hash armazenado: " + user.getPassword());
        System.out.println("Senha válida? " + isMatch);

        return isMatch;
    }


}