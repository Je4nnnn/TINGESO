package com.example.m2_prestamos.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MovimientoDTO {
    private Long id;
    private String tipo; 
    private Long herramientaId;
    private LocalDateTime fecha;
    private String usuario;
    private Integer cantidadAfectada;
}
