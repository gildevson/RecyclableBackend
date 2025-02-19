package com.reciclaveis.reciclaveis.service;

import com.reciclaveis.reciclaveis.entity.User;
import com.reciclaveis.reciclaveis.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean validateLogin(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (!userOptional.isPresent()) {
            System.out.println("Usuário não encontrado para o email: " + email);
            return false;
        }

        User user = userOptional.get();
        System.out.println("Usuário encontrado: " + user.getEmail());
        System.out.println("Permissões do usuário: " + user.getPermissions());

        System.out.println("Comparando senha fornecida com hash no banco...");
        boolean isMatch = passwordEncoder.matches(password, user.getPassword());
        System.out.println("Senha válida? " + isMatch);

        return isMatch;
    }

    public String getUserNameByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getName)
                .orElse("Usuário desconhecido");
    }
}
