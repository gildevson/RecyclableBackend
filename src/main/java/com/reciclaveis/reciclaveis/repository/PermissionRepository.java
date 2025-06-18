package com.reciclaveis.reciclaveis.repository;

import com.reciclaveis.reciclaveis.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    // Busca por descrição (ex: "admin", "usuario", "criar_usuario")
    Optional<Permission> findByDescription(String description);
}
