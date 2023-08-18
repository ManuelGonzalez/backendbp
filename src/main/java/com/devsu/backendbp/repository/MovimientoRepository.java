package com.devsu.backendbp.repository;

import com.devsu.backendbp.entity.Cliente;
import com.devsu.backendbp.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Date;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByCuentaNumeroCuenta(Long numeroCuenta);
    List<Movimiento> findByFechaBetweenAndCliente(Date fechaInicio, Date fechaFin, Cliente cliente);
}

