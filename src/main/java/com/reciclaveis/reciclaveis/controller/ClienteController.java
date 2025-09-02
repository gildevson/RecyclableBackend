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
    public ResponseEntity<?> create(@Valid @RequestBody ClienteRequestDTO dto) {

        // valida duplicidade
        if (dto.clienteCnpj() != null && clienteRepository.existsByClienteCnpj(dto.clienteCnpj())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CNPJ já cadastrado");
        }

        Cliente entity = new Cliente();
        entity.setClienteNome(dto.clienteNome());
        entity.setClienteCnpj(dto.clienteCnpj());
        entity.setClienteEmail(dto.clienteEmail());
        entity.setClienteTelefone(dto.clienteTelefone());
        entity.setClienteCelular(dto.clienteCelular());
        entity.setClienteEndereco(dto.clienteEndereco());
        entity.setClienteCidade(dto.clienteCidade());
        entity.setClienteBairro(dto.clienteBairro());
        entity.setClienteEstado(dto.clienteEstado());
        entity.setClienteNacionalidade(dto.clienteNacionalidade());
        entity.setClienteNumeroCasa(dto.clienteNumeroCasa());
        entity.setClienteComplemento(dto.clienteComplemento());
        entity.setClienteInscricaoMunicipal(dto.clienteInscricaoMunicipal());
        entity.setClienteInscricaoEstadual(dto.clienteInscricaoEstadual());
        entity.setClienteSituacao(dto.clienteSituacao());


        Cliente saved = clienteRepository.save(entity);
        ClienteResponseDTO response = new ClienteResponseDTO(
                saved.getId(),
                saved.getClienteNome(),
                saved.getClienteCnpj(),
                saved.getClienteEmail(),
                saved.getClienteTelefone(),
                saved.getClienteCelular(),
                saved.getClienteEndereco(),
                saved.getClienteCidade(),
                saved.getClienteBairro(),
                saved.getClienteEstado(),
                saved.getClienteNacionalidade(),
                saved.getClienteNumeroCasa(),
                saved.getClienteComplemento(),
                saved.getClienteInscricaoMunicipal(),
                saved.getClienteCpf(),
                saved.getClienteInscricaoEstadual(),
                saved.getClienteInscricaoMunicipal(),
                saved.getClienteSituacao(),
                saved.getCreatedAt(),


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
                        c.getClienteEndereco(),
                        c.getClienteCidade(),
                        c.getClienteBairro(),
                        c.getClienteEstado(),
                        c.getClienteNacionalidade(),
                        c.getClienteNumeroCasa(),
                        c.getClienteComplemento(),
                        c.getClienteInscricaoMunicipal(),
                        c.getClienteCpf(),
                        c.getClienteInscricaoEstadual(),
                        c.getClienteInscricaoMunicipal(),
                        c.getClienteSituacao(),
                        c.getCreatedAt()
                ))
                .toList();

        return ResponseEntity.ok(clientes);
    }
}
