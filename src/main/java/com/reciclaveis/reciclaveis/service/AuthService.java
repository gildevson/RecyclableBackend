package com.reciclaveis.reciclaveis.service;

import com.reciclaveis.reciclaveis.entity.User;
import com.reciclaveis.reciclaveis.repository.UserRepository;
import com.reciclaveis.reciclaveis.util.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean validateLogin(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmailWithPermissions(email);

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

    public User getUserByEmail(String email) {
        return userRepository.findByEmailWithPermissions(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado para o email: " + email));
    }



    public User getUserFromToken(String token) {
        try {
            String email = jwtService.extractUsername(token);
            if (email == null) {
                throw new SecurityException("Token inválido: email ausente.");
            }

            return userRepository.findByEmailWithPermissions(email)
                    .orElseThrow(() -> new SecurityException("Usuário não encontrado para o token."));
        } catch (Exception e) {
            throw new SecurityException("Falha ao processar o token: " + e.getMessage(), e);
        }
    }


    public void updatePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado para o ID: " + userId));

        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }


}
