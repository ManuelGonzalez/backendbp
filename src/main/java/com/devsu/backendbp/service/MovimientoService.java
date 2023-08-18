package com.devsu.backendbp.service;

import com.devsu.backendbp.entity.Cliente;
import com.devsu.backendbp.entity.Cuenta;
import com.devsu.backendbp.entity.Movimiento;
import com.devsu.backendbp.entity.dto.MovimientoDTO;
import com.devsu.backendbp.exception.MovimientosException;
import com.devsu.backendbp.repository.CuentaRepository;
import com.devsu.backendbp.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    CuentaRepository cuentaRepository;

    public List<MovimientoDTO> obtenerTodosLosMovimientos() {
        List<Movimiento> movimientos = movimientoRepository.findAll();
        return movimientos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<MovimientoDTO> obtenerMovimientoPorId(Long id) {
        Optional<Movimiento> movimiento = movimientoRepository.findById(id);
        return movimiento.map(this::convertToDTO);
    }

    public List<MovimientoDTO> obtenerMovimientosPorNumeroDeCuenta(Long numeroCuenta) {
        List<Movimiento> movimientos = movimientoRepository.findByCuentaNumeroCuenta(numeroCuenta);
        return movimientos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MovimientoDTO> obtenerMovimientosPorCuentayFecha(Date fechaInicio, Date fechaFin, Cliente cliente) {
        List<Movimiento> movimientos = movimientoRepository.findByFechaBetweenAndCliente(fechaInicio, fechaFin, cliente);
        return movimientos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MovimientoDTO guardarMovimiento(MovimientoDTO movimientoDTO) {
        movimientoDTO.setFecha(new Date());

        if ("DEBITO".equals(movimientoDTO.getTipoMovimiento()) && movimientoDTO.getValor() > 1000){
            throw new MovimientosException("Supera El limite de retiro");
        }

        Movimiento movimiento = convertToEntity(movimientoDTO);
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimientoDTO.getNumeroCuenta());

        // Validar que el saldo no sea negativo
        double nuevoSaldo = "DEBITO".equals(movimiento.getTipoMovimiento()) ?
                cuenta.getSaldo() - movimientoDTO.getValor():
                cuenta.getSaldo() + movimientoDTO.getValor();
        if (nuevoSaldo < 0) {
            throw new MovimientosException("Saldo no disponible");
        }

        // Actualizar el saldo de la cuenta
        cuenta.setSaldo(nuevoSaldo);
        cuentaRepository.save(cuenta);

        movimiento.setCuenta(cuenta);
        movimiento.setCliente(cuenta.getCliente());
        Movimiento movimientoGuardado = movimientoRepository.save(movimiento);

        return convertToDTO(movimientoGuardado);
    }

    public void eliminarMovimiento(Long id) {
        movimientoRepository.deleteById(id);
    }

    private MovimientoDTO convertToDTO(Movimiento movimiento) {

        String detalleMovimiento = "DEBITO".equals(movimiento.getTipoMovimiento()) ? "Retiro de " + movimiento.getValor():
                "Deposito de " + movimiento.getValor();

        return MovimientoDTO.builder()
                .numeroCuenta(movimiento.getCuenta().getNumeroCuenta())
                .tipoCuenta(movimiento.getCuenta().getTipoCuenta())
                .saldoInicial(movimiento.getSaldoInicial())
                .estado(movimiento.getCuenta().getEstado())
                .id(movimiento.getId())
                .fecha(movimiento.getFecha())
                .valor(movimiento.getValor())
                .tipoMovimiento(movimiento.getTipoMovimiento())
                .movimiento(detalleMovimiento)
                .build();
    }

    private Movimiento convertToEntity(MovimientoDTO movimientoDto) {

        Cuenta cuenta = cuentaRepository.getReferenceById(movimientoDto.getNumeroCuenta());

        return Movimiento.builder()
                .id(movimientoDto.getId())
                .saldoInicial(cuenta.getSaldo())
                .valor(movimientoDto.getValor())
                .fecha(movimientoDto.getFecha())
                .tipoMovimiento(movimientoDto.getTipoMovimiento())
                .cuenta(cuenta)
                .build();
    }


}
