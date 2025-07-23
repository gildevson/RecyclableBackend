package com.reciclaveis.reciclaveis.dto;

import java.util.List;

public class AuthResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String token;
    private List<PermissionDTO> permissions;

    public AuthResponseDTO() {}

    public AuthResponseDTO(String token, Long id, String name, String email, List<PermissionDTO> permissions) {
        this.token = token;
        this.id = id;
        this.name = name;
        this.email = email;
        this.permissions = permissions;
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public List<PermissionDTO> getPermissions() { return permissions; }
    public void setPermissions(List<PermissionDTO> permissions) { this.permissions = permissions; }

    public static class PermissionDTO {
        private Long id;
        private String description;

        public PermissionDTO() {}

        public PermissionDTO(Long id, String description) {
            this.id = id;
            this.description = description;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}
