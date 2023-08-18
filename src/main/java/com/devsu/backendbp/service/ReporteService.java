package com.devsu.backendbp.service;

import com.devsu.backendbp.entity.Cliente;
import com.devsu.backendbp.entity.dto.MovimientoDTO;
import com.devsu.backendbp.entity.dto.ReporteDTO;
import com.devsu.backendbp.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    MovimientoService movimientoService;

    @Autowired
    private ClienteRepository clienteRepository;

    public ReporteDTO generarReporte(Long clienteId, Date fechaInicio, Date fechaFin) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        List<MovimientoDTO> movimientos = movimientoService
                .obtenerMovimientosPorCuentayFecha(fechaInicio, fechaFin, cliente);

        if (movimientos.isEmpty()) {
            throw new RuntimeException("No se encontraron movimientos");
        }

        MovimientoDTO movimientoFinal = movimientos.get(movimientos.size()-1);

        double saldoFinal = movimientoFinal.getTipoMovimiento().equals("DEBITO") ?
            movimientoFinal.getSaldoInicial() - movimientoFinal.getValor() :
            movimientoFinal.getSaldoInicial() + movimientoFinal.getValor();

        ReporteDTO reporte = ReporteDTO.builder()
                .nombreCliente(cliente.getNombre())
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .numeroCuenta(movimientos.get(0).getNumeroCuenta())
                .tipoCuenta(movimientos.get(0).getTipoCuenta())
                .saldoInicial(movimientos.get(0).getSaldoInicial())
                .saldoFinal(saldoFinal)
                .movimientos(movimientos)
                .build();

        return reporte;
    }
}
