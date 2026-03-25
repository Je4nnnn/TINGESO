package com.example.m2_prestamos.dto;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String estado;
}
