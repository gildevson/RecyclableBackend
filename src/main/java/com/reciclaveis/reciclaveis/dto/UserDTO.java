package com.reciclaveis.reciclaveis.dto;

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
        this.permissions = user.getPermissions().stream()
                .map(PermissionDTO::new)
                .collect(Collectors.toSet());
    }
}
