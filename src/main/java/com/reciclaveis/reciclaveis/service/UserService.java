package com.reciclaveis.reciclaveis.service;

import com.reciclaveis.reciclaveis.entity.Permission;
import com.reciclaveis.reciclaveis.entity.User;
import com.reciclaveis.reciclaveis.repository.PermissionRepository;
import com.reciclaveis.reciclaveis.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final BCryptPasswordEncoder encoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, PermissionRepository permissionRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.encoder = encoder;
    }

    /**
     * Registra um novo usuário com ou sem permissão definida.
     */
    public void registerUser(String email, String rawPassword, String name, Long permissionId) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O email é obrigatório.");
        }
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }

        String hashedPassword = encoder.encode(rawPassword);
        logger.info("Hash gerado para a senha do email {}: {}", email, hashedPassword);

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(hashedPassword);
        newUser.setName(name);

        // Define permissões
        Set<Permission> permissions = new HashSet<>();
        if (permissionId == null) {
            Permission defaultPermission = permissionRepository.findByDescription("usuario")
                    .orElseThrow(() -> new IllegalArgumentException("Permissão padrão 'usuario' não encontrada"));
            permissions.add(defaultPermission);
        } else {
            Permission permission = permissionRepository.findById(permissionId)
                    .orElseThrow(() -> new IllegalArgumentException("Permissão não encontrada"));
            permissions.add(permission);
        }

        newUser.setPermissions(permissions);
        userRepository.save(newUser);
        logger.info("Usuário {} registrado com sucesso.", email);
    }

    /**
     * Retorna todos os usuários com suas permissões carregadas.
     */
    public List<User> findAll() {
        return userRepository.findAllWithPermissions();
    }

    /**
     * Atribui uma nova permissão a um usuário existente.
     */
    public void addPermissionToUser(Long userId, Long permissionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new IllegalArgumentException("Permissão não encontrada"));

        user.getPermissions().add(permission);
        userRepository.save(user);
    }
}
