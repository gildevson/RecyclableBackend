package com.reciclaveis.reciclaveis.controller;


import com.reciclaveis.reciclaveis.entity.Cliente;
import com.reciclaveis.reciclaveis.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository ;

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente) {
        if (cliente.getClienteCnpj() != null &&
        clienteRepository.existsByClienteCnpj(cliente.getClienteCnpj())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("CNPJ já cadastrado");
        }

        Cliente saved = clienteRepository.save(cliente);
        URI location = URI.create("/clientes/" + saved.getId());
        return ResponseEntity.created(location).body(saved);

    }


}
