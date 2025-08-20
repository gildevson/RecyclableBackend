package com.reciclaveis.reciclaveis.controller;

import com.reciclaveis.reciclaveis.dto.ClienteRequestDTO;
import com.reciclaveis.reciclaveis.dto.ClienteResponseDTO;
import com.reciclaveis.reciclaveis.entity.Cliente;
import com.reciclaveis.reciclaveis.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody ClienteRequestDTO dto) {


        if (dto.clienteCnpj() != null) {
            System.out.println("cnpj já cadastrado!");


            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CNPJ JÁ CADASTRADO");

        }

        // mapeia DTO -> entidade
        Cliente entity = new Cliente();
        entity.setClienteNome(dto.clienteNome());
        entity.setClienteCnpj(dto.clienteCnpj());
        entity.setClienteEmail(dto.clienteEmail());
        entity.setClienteTelefone(dto.clienteTelefone());
        entity.setClienteCelular(dto.clienteCelular());

        Cliente saved = clienteRepository.save(entity);

        // mapeia entidade -> ResponseDTO
        ClienteResponseDTO response = new ClienteResponseDTO(
                saved.getId(),
                saved.getClienteNome(),
                saved.getClienteCnpj(),
                saved.getClienteEmail(),
                saved.getClienteTelefone(),
                saved.getClienteCelular(),
                saved.getCreatedAt()
        );

        return ResponseEntity
                .created(URI.create("/clientes/" + saved.getId()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> getAll() {
        List<ClienteResponseDTO> clientes = clienteRepository.findAll().stream()
                .map(c -> new ClienteResponseDTO(
                        c.getId(),
                        c.getClienteNome(),
                        c.getClienteCnpj(),
                        c.getClienteEmail(),
                        c.getClienteTelefone(),
                        c.getClienteCelular(),
                        c.getCreatedAt()
                ))
                .toList();

        return ResponseEntity.ok(clientes);
    }
}
