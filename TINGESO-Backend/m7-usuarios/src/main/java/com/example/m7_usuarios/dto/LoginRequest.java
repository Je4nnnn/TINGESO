package com.example.m7_usuarios.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
