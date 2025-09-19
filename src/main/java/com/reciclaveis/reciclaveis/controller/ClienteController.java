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
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:3000"}, allowCredentials = "true")
public class ClienteController {

    private final ClienteRepository repo;
    public ClienteController(ClienteRepository repo) { this.repo = repo; }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ClienteRequestDTO dto) {
        if (dto.clienteCnpjCpf()!=null && repo.existsByClienteCnpjCpf(dto.clienteCnpjCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CNPJ já cadastrado");
        }
        Cliente e = new Cliente();
        copy(dto, e);
        Cliente saved = repo.save(e);
        return ResponseEntity.created(URI.create("/clientes/"+saved.getClienteid()))
                .body(toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> getAll() {
        return ResponseEntity.ok(
                repo.findAll().stream().map(this::toResponse).toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        return repo.findById(id)
                .<ResponseEntity<Object>>map(c -> ResponseEntity.ok(toResponse(c)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody ClienteRequestDTO dto) {
        return repo.findById(id).map(existing -> {
            if (dto.clienteCnpjCpf()!=null &&
                    repo.existsByClienteCnpjCpfAndClienteidNot(dto.clienteCnpjCpf(), id)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("CNPJ já cadastrado");
            }
            copy(dto, existing);
            Cliente saved = repo.save(existing);
            return ResponseEntity.ok(toResponse(saved));
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado"));
    }

    private void copy(ClienteRequestDTO d, Cliente e) {
        e.setClienteNome(d.clienteNome());
        e.setClienteRazao(d.clienteRazao());
        e.setClienteCnpjCpf(d.clienteCnpjCpf());
        e.setClienteEmail(d.clienteEmail());
        e.setClienteTelefone(d.clienteTelefone());
        e.setClienteCelular(d.clienteCelular());
        e.setClienteNomeContato(d.clienteNomeContato());
        e.setClienteEndereco(d.clienteEndereco());
        e.setClienteBairro(d.clienteBairro());
        e.setClienteCidade(d.clienteCidade());
        e.setClienteEstado(d.clienteEstado());
        e.setClienteNacionalidade(d.clienteNacionalidade());
        e.setClienteNumeroCasa(d.clienteNumeroCasa());
        e.setClienteComplemento(d.clienteComplemento());
        e.setClienteInscricaoMunicipal(d.clienteInscricaoMunicipal());
        e.setClienteInscricaoEstadual(d.clienteInscricaoEstadual());
        e.setClienteSituacao(d.clienteSituacao());
        // createdAt fica com @CreationTimestamp (não copie do request)
    }

    private ClienteResponseDTO toResponse(Cliente c) {
        return new ClienteResponseDTO(
                c.getClienteid(),
                c.getClienteNome(),
                c.getClienteRazao(),
                c.getClienteCnpjCpf(),
                c.getClienteEmail(),
                c.getClienteTelefone(),
                c.getClienteCelular(),
                c.getClienteNomeContato(),
                c.getClienteEndereco(),
                c.getClienteBairro(),
                c.getClienteCidade(),
                c.getClienteEstado(),
                c.getClienteNacionalidade(),
                c.getClienteNumeroCasa(),
                c.getClienteComplemento(),
                c.getClienteInscricaoMunicipal(),
                c.getClienteInscricaoEstadual(),
                c.getClienteSituacao(),
                c.getCreatedAt()
        );
    }
}
