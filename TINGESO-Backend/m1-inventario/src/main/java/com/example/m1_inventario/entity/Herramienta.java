package com.example.m1_inventario.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Herramienta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String categoria;
    private String estado; // DISPONIBLE, PRESTADA, EN_REPARACION, DADA_DE_BAJA
    private Double valorReposicion;
}
