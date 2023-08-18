package com.devsu.backendbp.entity.dto;

import lombok.*;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuentaDTO {

    @NotNull(message = "El número de cuenta no puede ser nulo")
    private Long numeroCuenta;

    @Pattern(regexp = "CORRIENTE|AHORRO", message = "El tipo de cuenta debe ser 'CORRIENTE' o 'AHORRO'")
    private String tipoCuenta;

    @Min(value = 0, message = "El saldo no puede ser negativo")
    private double saldo;

    private boolean estado;

    @NotNull(message = "El ID del cliente no puede ser nulo")
    private Long clienteId;

    private String nombreCliente;
}
