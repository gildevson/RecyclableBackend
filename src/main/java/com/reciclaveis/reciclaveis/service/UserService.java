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
    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    public UserService(UserRepository userRepository,
                       PermissionRepository permissionRepository,
                       BCryptPasswordEncoder encoder,
                       AuthService authService) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.encoder = encoder;
        this.authService = authService;
    }

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

        Set<Permission> permissions = new HashSet<>();

        if (permissionId == null) {
            logger.warn("permissionId não fornecido, usando permissão padrão 'usuario'");
            Permission defaultPermission = permissionRepository.findByDescription("usuario")
                    .orElseThrow(() -> new IllegalArgumentException("Permissão padrão 'usuario' não encontrada"));
            permissions.add(defaultPermission);
        } else {
            logger.info("Buscando permissão com ID {}", permissionId);

            Optional<Permission> optionalPermission = permissionRepository.findById(permissionId);
            if (optionalPermission.isEmpty()) {
                logger.error("Permissão com ID {} não encontrada no banco", permissionId);
                throw new IllegalArgumentException("Permissão com ID " + permissionId + " não encontrada.");
            }

            permissions.add(optionalPermission.get());
            logger.info("Permissão {} adicionada ao novo usuário.", optionalPermission.get().getDescription());
        }

        newUser.setPermissions(permissions);
        userRepository.save(newUser);
        logger.info("Usuário {} registrado com sucesso.", email);
    }

    public List<User> findAll() {
        return userRepository.findAllWithPermissions();
    }

    public void addPermissionToUser(Long userId, Long permissionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new IllegalArgumentException("Permissão não encontrada"));

        user.getPermissions().add(permission);
        userRepository.save(user);
        logger.info("Permissão {} adicionada ao usuário {}", permissionId, userId);
    }

    public void deleteUserIfAuthorized(Long userIdToDelete, String token) {
        try {
            String cleanedToken = token.replace("Bearer ", "").trim();
            logger.info("Token limpo recebido: {}", cleanedToken);

            User requestingUser = authService.getUserFromToken(cleanedToken);
            logger.info("Usuário autenticado: {} (ID: {})", requestingUser.getEmail(), requestingUser.getId());
            logger.info("ID do usuário a excluir: {}", userIdToDelete);
            logger.info("ID do usuário autenticado: {}", requestingUser.getId());
            if (requestingUser.getId().equals(userIdToDelete)) {
                throw new SecurityException("Você não pode excluir seu próprio usuário.");
            }

            if (userIdToDelete.equals(1L)) {
                logger.warn("Tentativa de excluir o usuário administrador padrão.");
                throw new SecurityException("Você não pode excluir o usuário administrador padrão.");
            }

            Set<Permission> permissions = requestingUser.getPermissions();

            logger.info("Permissões carregadas do usuário:");
            permissions.forEach(p -> logger.info(" - Permissão: id={}, descricao={}", p.getId(), p.getDescription()));

            boolean isAdmin = permissions.stream()
                    .anyMatch(p -> p.getId() != null && (
                            p.getId().equals(1L) ||
                                    p.getDescription().toLowerCase().contains("total")
                    ));

            if (!isAdmin) {
                logger.warn("Usuário não tem permissão para excluir.");
                throw new SecurityException("Você não tem permissão para excluir usuários.");
            }

            logger.info("Tentando excluir usuário com ID: {}", userIdToDelete);
            try {
                userRepository.deleteById(userIdToDelete);
                logger.info("Usuário com ID {} excluído com sucesso por {}", userIdToDelete, requestingUser.getEmail());
            } catch (Exception ex) {
                logger.error("Erro ao tentar excluir usuário com ID {}: {}", userIdToDelete, ex.getMessage(), ex);
                throw ex;
            }


        } catch (SecurityException se) {
            logger.error("Erro de segurança ao excluir usuário: {}", se.getMessage());
            throw se;
        } catch (Exception e) {
            logger.error("Erro inesperado ao excluir usuário: {}", e.getMessage(), e);
            throw new RuntimeException("Erro interno ao excluir o usuário. Detalhes no log.");
        }
    }
}
