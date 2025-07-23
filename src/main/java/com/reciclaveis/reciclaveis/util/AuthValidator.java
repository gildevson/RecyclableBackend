package com.reciclaveis.reciclaveis.util;

import com.reciclaveis.reciclaveis.service.AuthService;
import org.springframework.stereotype.Component;

@Component
public class AuthValidator {

    private final AuthService authService;

    public AuthValidator(AuthService authService) {
        this.authService = authService;
    }

    public boolean isLoginValid(String email, String password) {
        return authService.validateLogin(email, password);
    }
}
