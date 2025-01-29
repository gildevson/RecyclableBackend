package com.reciclaveis.reciclaveis.service;

import com.reciclaveis.reciclaveis.entity.User;
import com.reciclaveis.reciclaveis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public boolean validateLogin(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            // Verifica a senha usando BCrypt
            return BCrypt.checkpw(password, user.getPassword());
        }
        return false;
    }
}
