package com.example.m6_reportes.dto;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String correo;
    private String telefono;
    private String estado;
}
