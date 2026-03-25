package com.example.m2_prestamos.dto;

import lombok.Data;

@Data
public class HerramientaDTO {
    private Long id;
    private String nombre;
    private String estado;
    private Double valorReposicion;
}
