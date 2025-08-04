package com.reciclaveis.reciclaveis.repository;

import com.reciclaveis.reciclaveis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

}
