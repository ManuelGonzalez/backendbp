package com.devsu.backendbp.service;

import com.devsu.backendbp.entity.Cliente;
import com.devsu.backendbp.entity.Cuenta;
import com.devsu.backendbp.entity.dto.CuentaDTO;
import com.devsu.backendbp.repository.ClienteRepository;
import com.devsu.backendbp.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Value("${limite.diario.retiro}")
    private double LIMITE_DIARIO_RETIRO;

    public List<CuentaDTO> obtenerCuentas() {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        return cuentas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CuentaDTO> obtenerCuentaPorId(Long numeroCuenta) {
        Optional<Cuenta> cuenta = cuentaRepository.findById(numeroCuenta);
        return cuenta.map(this::convertToDTO);
    }

    public CuentaDTO guardarCuenta(CuentaDTO cuentaDTO) {
        Cuenta savedCuenta = cuentaRepository.save(convertToEntity(cuentaDTO));
        return convertToDTO(savedCuenta);
    }

    public void eliminarCuenta(Long numeroCuenta) {
        cuentaRepository.deleteById(numeroCuenta);
    }

    public CuentaDTO actualizarCuenta(Long numeroCuenta, CuentaDTO cuentaActualizada) {
        if (cuentaRepository.existsById(numeroCuenta)) {
            cuentaActualizada.setNumeroCuenta(numeroCuenta);
            Cuenta updatedCuenta = cuentaRepository.save(convertToEntity(cuentaActualizada));
            return convertToDTO(updatedCuenta);
        }
        return null;
    }

    public CuentaDTO retirarDinero(Long id, double monto) {
        Optional<Cuenta> cuentaOpt = cuentaRepository.findById(id);

        if (cuentaOpt.isPresent()) {
            Cuenta cuenta = cuentaOpt.get();
            double nuevoSaldo = cuenta.getSaldo() - monto;

            if (monto > LIMITE_DIARIO_RETIRO) {
                throw new RuntimeException("Cupo diario Excedido");
            }

            if (nuevoSaldo < 0) {
                throw new RuntimeException("Saldo no disponible");
            }

            cuenta.setSaldo(nuevoSaldo);
            Cuenta cuentaActualizada = cuentaRepository.save(cuenta);
            return convertToDTO(cuentaActualizada);

        } else {
            throw new RuntimeException("Cuenta no encontrada");
        }
    }

    private CuentaDTO convertToDTO(Cuenta cuenta) {
        return CuentaDTO.builder()
                .numeroCuenta(cuenta.getNumeroCuenta())
                .saldo(cuenta.getSaldo())
                .clienteId(cuenta.getCliente().getId())
                .nombreCliente(cuenta.getCliente().getNombre())
                .estado(cuenta.getEstado())
                .tipoCuenta(cuenta.getTipoCuenta())
                .build();
    }

    private Cuenta convertToEntity(CuentaDTO cuentaDto) {

        Cliente cliente = clienteRepository.getReferenceById(cuentaDto.getClienteId());

        return Cuenta.builder()
                .numeroCuenta(cuentaDto.getNumeroCuenta())
                .tipoCuenta(cuentaDto.getTipoCuenta())
                .saldo(cuentaDto.getSaldo())
                .cliente(cliente)
                .estado(cuentaDto.isEstado())
                .build();
    }
}
