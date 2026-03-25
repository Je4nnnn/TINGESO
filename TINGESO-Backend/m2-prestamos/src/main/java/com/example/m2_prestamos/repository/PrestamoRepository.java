package com.example.m2_prestamos.repository;

import com.example.m2_prestamos.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByClienteId(Long clienteId);
    List<Prestamo> findByEstado(String estado);
    List<Prestamo> findByClienteIdAndEstado(Long clienteId, String estado);
    List<Prestamo> findByClienteIdAndHerramientaIdAndEstado(Long clienteId, Long herramientaId, String estado);
}
