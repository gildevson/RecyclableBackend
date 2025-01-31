package com.reciclaveis.reciclaveis.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "1234567"; // Substitua pela senha que está tentando logar
        String hashedPassword = encoder.encode(rawPassword);
        System.out.println("Senha original: " + rawPassword);
        System.out.println("Hash gerado: " + hashedPassword);
    }
}