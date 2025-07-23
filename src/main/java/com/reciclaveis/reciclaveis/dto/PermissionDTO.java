package com.reciclaveis.reciclaveis.dto;

import com.reciclaveis.reciclaveis.entity.Permission;
import lombok.Data;

@Data
public class PermissionDTO {
    private Long id;
    private String name;
    private String description;

    public PermissionDTO(Permission p) {
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
    }
}
