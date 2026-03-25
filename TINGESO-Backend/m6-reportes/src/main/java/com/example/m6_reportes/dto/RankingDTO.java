package com.example.m6_reportes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RankingDTO {
    private HerramientaDTO herramienta;
    private Long cantidadPrestamos;
}
