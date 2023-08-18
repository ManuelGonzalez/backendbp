package com.devsu.backendbp.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cliente")
public class Cliente extends Persona {

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "estado")
    private Boolean estado;

}
