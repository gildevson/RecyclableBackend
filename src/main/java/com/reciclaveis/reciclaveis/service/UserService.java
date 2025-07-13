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

    /**
     * Exclui um usuário se quem está autenticado possuir permissão total (ID 1).
     */
    public void deleteUserIfAuthorized(Long userIdToDelete, String token) {
        String cleanedToken = token.replace("Bearer ", "").trim();
        User requestingUser = authService.getUserFromToken(cleanedToken);

        boolean hasPermissionTotal = requestingUser.getPermissions().stream()
                .anyMatch(p -> p.getId() == 1);

        System.out.println("USUARIO AUTENTICADO: " + requestingUser.getEmail());
        System.out.println("Permissões encontradas: ");

        requestingUser.getPermissions().forEach(p -> System.out.println(" - Permissão ID: " + p.getId()));

        System.out.println("Token recebido: " + token);
        System.out.println("Usuário autenticado: " + requestingUser.getEmail());
        System.out.println("ID do usuário autenticado: " + requestingUser.getId());
        System.out.println("ID do usuário a ser excluído: " + userIdToDelete);

        if (!hasPermissionTotal) {
            throw new SecurityException("Você não tem permissão para excluir usuários.");
        }

        if (requestingUser.getId().equals(userIdToDelete)) {
            throw new SecurityException("Você não pode excluir seu próprio usuário.");
        }

        if (userIdToDelete == 1L) {
            throw new SecurityException("Você não pode excluir o usuário administrador padrão.");
        }

        userRepository.deleteById(userIdToDelete);
    }
}
