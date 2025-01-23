package com.reciclaveis.reciclaveis.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;
import java.util.UUID;


@Data
@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "permissions") // Relacionamento bidirecional
    private Set<User> users;
}