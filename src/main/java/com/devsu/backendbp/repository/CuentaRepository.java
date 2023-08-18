package com.devsu.backendbp.repository;

import com.devsu.backendbp.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Cuenta findByNumeroCuenta(Long numeroCuenta);
}
