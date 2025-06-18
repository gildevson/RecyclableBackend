package com.reciclaveis.reciclaveis.dto;

import com.reciclaveis.reciclaveis.entity.Permission;
import lombok.Data;

@Data
public class PermissionDTO {
    private Long id;
    private String description;

    public PermissionDTO(Permission permission) {
        this.id = permission.getId();
        this.description = permission.getDescription();
    }
}
