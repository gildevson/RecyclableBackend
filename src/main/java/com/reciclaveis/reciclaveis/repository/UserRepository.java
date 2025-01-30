package com.reciclaveis.reciclaveis.repository;


import com.reciclaveis.reciclaveis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}