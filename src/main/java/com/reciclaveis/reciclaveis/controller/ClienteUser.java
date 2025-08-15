package com.reciclaveis.reciclaveis.controller;


import com.reciclaveis.reciclaveis.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Clientes")
public class ClienteUser {

    @Autowired
    private ClienteRepository clienteRepository ;


}
