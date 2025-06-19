package com.reciclaveis.reciclaveis.controller;

import com.reciclaveis.reciclaveis.entity.Permission;
import com.reciclaveis.reciclaveis.repository.PermissionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/permissions")
public class PermissionController {

    private final PermissionRepository permissionRepository;

    public PermissionController(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @GetMapping
    public ResponseEntity<List<Permission>> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return ResponseEntity.ok(permissions);
    }
}
