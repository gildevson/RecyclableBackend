package com.reciclaveis.reciclaveis.repository;


import com.reciclaveis.reciclaveis.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    boolean existsByClienteCnpj(String clienteCnpj);
    Optional<Cliente> findByClienteCnpj(String clienteCnpj);
}