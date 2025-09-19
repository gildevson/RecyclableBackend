package com.reciclaveis.reciclaveis.repository;

import com.reciclaveis.reciclaveis.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    boolean existsByClienteCnpjCpf(String clienteCnpjCpf);

    // Se quiser consultar por CNPJ (1 parâmetro só):
    Optional<Cliente> findByClienteCnpjCpf(String clienteCnpjCpf);

    // Para checar duplicidade no update (excluindo o próprio registro):
    boolean existsByClienteCnpjCpfAndClienteidNot(String clienteCnpjCpf, UUID id);
}
