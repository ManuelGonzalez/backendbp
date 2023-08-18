package com.devsu.backendbp.entity.dto;

import lombok.*;
import javax.validation.constraints.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDTO extends PersonaDTO {

    private Long id;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String contrasena;

    private boolean estado;
}