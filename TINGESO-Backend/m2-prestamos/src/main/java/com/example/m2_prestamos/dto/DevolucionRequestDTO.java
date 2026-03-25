package com.example.m2_prestamos.dto;

import lombok.Data;

@Data
public class DevolucionRequestDTO {
    private Long prestamoId;
    private String estadoDevolucion; // OK, DANO_LEVE, DANO_IRREPARABLE
}
