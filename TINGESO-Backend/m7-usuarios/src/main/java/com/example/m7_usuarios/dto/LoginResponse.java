package com.example.m7_usuarios.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token; // For this simple implementation, maybe just username
    private String rol;
    private Long id;
}
