package com.reciclaveis.reciclaveis.repository;

import com.reciclaveis.reciclaveis.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    // Métodos personalizados, se necessário
}