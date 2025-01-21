package com.reciclaveis.reciclaveis.initializer;

import com.reciclaveis.reciclaveis.entity.Permission;
import com.reciclaveis.reciclaveis.entity.User;
import com.reciclaveis.reciclaveis.repository.PermissionRepository;
import com.reciclaveis.reciclaveis.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    public DataInitializer(UserRepository userRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void run(String... args) {
        // Verificar se já existem permissões e usuários no banco de dados
        if (permissionRepository.count() == 0 && userRepository.count() == 0) {
            // Criar permissão
            Permission adminPermission = new Permission();
            adminPermission.setName("ADMIN");
            adminPermission.setDescription("Acesso total ao sistema");
            permissionRepository.save(adminPermission);

            // Criar usuário com permissão
            User adminUser = new User();
            adminUser.setName("João Silva");
            adminUser.setEmail("joao@email.com");
            adminUser.setPassword("senhaSegura123"); // Recomenda-se criptografar a senha
            adminUser.setPermissions(Set.of(adminPermission));
            userRepository.save(adminUser);

            System.out.println("Usuário e permissão criados com sucesso!");
        } else {
            System.out.println("Usuário e permissão já existem no banco de dados.");
        }
    }
}
