package com.example.m6_reportes.service;

import com.example.m6_reportes.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReporteService {

    @Autowired
    private RestTemplate restTemplate;

    private final String M1_URL = "http://m1-inventario/api/herramientas";
    private final String M2_URL = "http://m2-prestamos/api/prestamos";
    private final String M3_URL = "http://m3-clientes/api/clientes";
    private final String M5_URL = "http://m5-kardex/api/kardex";

    public List<PrestamoDTO> getPrestamosActivos() {
        List<PrestamoDTO> vigentes = restTemplate.exchange(M2_URL + "/vigentes", HttpMethod.GET, null, new ParameterizedTypeReference<List<PrestamoDTO>>() {}).getBody();
        List<PrestamoDTO> atrasados = restTemplate.exchange(M2_URL + "/atrasados", HttpMethod.GET, null, new ParameterizedTypeReference<List<PrestamoDTO>>() {}).getBody();
        
        return Stream.concat(
                vigentes != null ? vigentes.stream() : Stream.empty(), 
                atrasados != null ? atrasados.stream() : Stream.empty()
        ).collect(Collectors.toList());
    }

    public List<ClienteDTO> getClientesConAtrasos() {
        List<PrestamoDTO> atrasados = restTemplate.exchange(M2_URL + "/atrasados", HttpMethod.GET, null, new ParameterizedTypeReference<List<PrestamoDTO>>() {}).getBody();
        if (atrasados == null || atrasados.isEmpty()) return Collections.emptyList();

        Set<Long> clienteIds = atrasados.stream().map(PrestamoDTO::getClienteId).collect(Collectors.toSet());
        return clienteIds.stream().map(id -> restTemplate.getForObject(M3_URL + "/" + id, ClienteDTO.class))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<RankingDTO> getRankingHerramientas(LocalDateTime start, LocalDateTime end) {
        String url = M5_URL + "/fechas?start=" + start.toString() + "&end=" + end.toString();
        List<MovimientoDTO> movimientos = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<MovimientoDTO>>() {}).getBody();
        
        if (movimientos == null || movimientos.isEmpty()) return Collections.emptyList();

        Map<Long, Long> conteo = movimientos.stream()
                .filter(m -> "PRESTAMO".equals(m.getTipo()))
                .collect(Collectors.groupingBy(MovimientoDTO::getHerramientaId, Collectors.counting()));

        return conteo.entrySet().stream()
                .map(e -> {
                    HerramientaDTO h = restTemplate.getForObject(M1_URL + "/" + e.getKey(), HerramientaDTO.class);
                    return new RankingDTO(h, e.getValue());
                })
                .sorted((a, b) -> b.getCantidadPrestamos().compareTo(a.getCantidadPrestamos()))
                .collect(Collectors.toList());
    }
}
