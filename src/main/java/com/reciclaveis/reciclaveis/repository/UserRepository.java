package com.reciclaveis.reciclaveis.repository;


import com.reciclaveis.reciclaveis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
