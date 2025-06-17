/*package com.reciclaveis.reciclaveis.entity;
import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
@Table(name = "user_permissions")
public class UserPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) ddd
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "permission_id", nullable = false
    )
    private Set<Permission> permission;
}*/