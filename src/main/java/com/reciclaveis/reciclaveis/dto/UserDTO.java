package com.reciclaveis.reciclaveis.dto;

import com.reciclaveis.reciclaveis.entity.Permission;
import com.reciclaveis.reciclaveis.entity.User;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Set<PermissionDTO> permissions;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.permissions = user.getPermissions()
                .stream()
                .map(PermissionDTO::new)
                .collect(Collectors.toSet());
    }

    @Data
    public static class PermissionDTO {
        private Long id;
        private String name;
        private String description;

        public PermissionDTO(Permission permission) {
            this.id = permission.getId();
            this.name = permission.getName();
            this.description = permission.getDescription();
        }
    }
}
