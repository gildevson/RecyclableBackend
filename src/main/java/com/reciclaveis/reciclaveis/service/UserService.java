package com.reciclaveis.reciclaveis.service;

import com.reciclaveis.reciclaveis.entity.Permission;
import com.reciclaveis.reciclaveis.entity.User;
import com.reciclaveis.reciclaveis.repository.PermissionRepository;
import com.reciclaveis.reciclaveis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void registerUser(String email, String rawPassword, String name, Long permissionId) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("O email é obrigatório.");
        }
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }

        String hashedPassword = encoder.encode(rawPassword);
        logger.info("Hash gerado para a senha do email {}: {}", email, hashedPassword);

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(hashedPassword);
        newUser.setName(name);

        // Busca e atribui a permissão
        if (permissionId != null) {
            Permission permission = permissionRepository.findById(permissionId)
                    .orElseThrow(() -> new IllegalArgumentException("Permissão não encontrada"));

            Set<Permission> permissions = new HashSet<>();
            permissions.add(permission);
            newUser.setPermissions(permissions);
        }

        userRepository.save(newUser);
        logger.info("Usuário {} registrado com sucesso.", email);
    }

    public List<User> findAll() {
        return userRepository.findAllWithPermissions(); // Correto
    }

    public void addPermissionToUser(Long userId, Long permissionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new IllegalArgumentException("Permissão não encontrada"));

        user.getPermissions().add(permission);
        userRepository.save(user);
    }
}
