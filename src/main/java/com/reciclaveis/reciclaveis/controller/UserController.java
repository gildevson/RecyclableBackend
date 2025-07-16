package com.reciclaveis.reciclaveis.controller;

import com.reciclaveis.reciclaveis.dto.UserDTO;
import com.reciclaveis.reciclaveis.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000") // Permite requisições do frontend React
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // Injeção via construtor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint para registrar um novo usuário
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");
            String name = request.get("name");

            Long permissionId = request.containsKey("permission_id")
                    ? Long.parseLong(request.get("permission_id"))
                    : null;

            userService.registerUser(email, password, name, permissionId);
            return ResponseEntity.ok("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para listar todos os usuários com suas permissões
    // Endpoint para listar todos os usuários com suas permissões
    @GetMapping
    public ResponseEntity<List<UserDTO>> listAllUsers(
            @RequestHeader(value = "Authorization", required = false) String token) {
        List<UserDTO> users = userService.findAll()
                .stream()
                .map(UserDTO::new)
                .toList();

        return ResponseEntity.ok(users);
    }


    // Endpoint para atribuir uma permissão a um usuário
    @PostMapping("/{userId}/permissions/{permissionId}")
    public ResponseEntity<String> addPermissionToUser(
            @PathVariable Long userId,
            @PathVariable Long permissionId) {
        try {
            userService.addPermissionToUser(userId, permissionId);
            return ResponseEntity.ok("Permissão atribuída com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        try {
            userService.deleteUserIfAuthorized(id, token);
            return ResponseEntity.ok("Usuário excluído com sucesso.");
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao excluir usuário: " + e.getMessage());
        }
    }



}

