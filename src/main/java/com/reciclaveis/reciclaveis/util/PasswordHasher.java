package com.reciclaveis.reciclaveis.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Substitua pela senha a ser testada
        String rawPassword = "1234567";
        String hashedPassword = encoder.encode(rawPassword);

        System.out.println("Senha original: " + rawPassword);
        System.out.println("Hash gerado: " + hashedPassword);

        // Verifica se a senha original corresponde ao hash gerado
        boolean matches = encoder.matches(rawPassword, hashedPassword);
        System.out.println("Senha válida? " + matches);
    }
}
