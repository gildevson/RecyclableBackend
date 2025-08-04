//package com.reciclaveis.reciclaveis.config;
//
//import com.reciclaveis.reciclaveis.service.EmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class EmailStartupTest implements CommandLineRunner {
//
//    @Autowired
//    private EmailService emailService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        String destino = "gildevson@gmail.com"; // troque pelo seu e-mail real
//
//        System.out.println("⏳ Testando envio de e-mail para " + destino);
//
//        try {
//            emailService.sendEmail(destino, "✅ Teste SMTP", "Macuco é um paizão muito gente boa, melhor programador do mundo, assiando Gilson");
//            System.out.println("✅ E-mail enviado com sucesso para " + destino);
//        } catch (Exception e) {
//            System.err.println("❌ Falha ao enviar e-mail: " + e.getMessage());
//            e.printStackTrace();
//        }
////    }
////} ESSE
