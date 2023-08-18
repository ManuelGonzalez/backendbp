package com.devsu.backendbp.entity.dto;

import java.util.Date;
import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReporteDTO {

    private Long clienteId;
    private String nombreCliente;
    private Date fechaInicio;
    private Date fechaFin;
    private Long numeroCuenta;
    private String tipoCuenta;
    private double saldoInicial;
    private double saldoFinal;
    private List<MovimientoDTO> movimientos;
}

