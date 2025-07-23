package com.reciclaveis.reciclaveis.controller;

import com.reciclaveis.reciclaveis.dto.EmailRequestDTO;
import com.reciclaveis.reciclaveis.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestEmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDTO request) {
        emailService.sendEmail(request.getTo(), request.getSubject(), request.getBody());
        return ResponseEntity.ok("Email enviado com sucesso para " + request.getTo());
    }
}