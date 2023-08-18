package com.devsu.backendbp.entity.dto;

import java.util.Date;
import lombok.*;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovimientoDTO {

    private Long id;

    private Date fecha;

    @Pattern(regexp = "CREDITO|DEBITO", message = "El tipo de movimiento debe ser 'CREDITO' o 'DEBITO'")
    private String tipoMovimiento;

    @Min(value = 0, message = "El valor no puede ser negativo")
    @NotNull
    private double valor;

    private double saldoInicial;

    @NotNull(message = "El número de cuenta no puede ser nulo")
    private Long numeroCuenta;

    private String tipoCuenta;

    private String movimiento;

    private boolean estado;
}

