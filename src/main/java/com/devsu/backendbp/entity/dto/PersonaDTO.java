package com.devsu.backendbp.entity.dto;

import jakarta.persistence.Id;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class PersonaDTO {

    @Id
    @NotNull(message = "La identificación no puede ser nula")
    private String identificacion;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Pattern(regexp = "M|F", message = "El género debe ser 'M' o 'F'")
    private String genero;

    @Min(value = 18, message = "La edad debe ser como mínimo 18")
    private int edad;

    private String direccion;

    @NotBlank(message = "El teléfono no puede estar vacío")
    private String telefono;
}
