package com.reciclaveis.reciclaveis.dto;

import com.reciclaveis.reciclaveis.entity.Permission;
import lombok.Data;

@Data
public class PermissionDTO {
    private Long id;
    private String name;
    private String description;

    public PermissionDTO(Permission permission) {
        this.id = permission.getId();
        this.name = permission.getName();
        this.description = permission.getDescription();
    }
}
