package com.devsu.backendbp.controller;

import com.devsu.backendbp.entity.dto.MovimientoDTO;
import com.devsu.backendbp.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimientos")
@Validated
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<MovimientoDTO> crearMovimiento(@Valid @RequestBody MovimientoDTO movimientoDTO) {
        MovimientoDTO newMovimiento = movimientoService.guardarMovimiento(movimientoDTO);
        return new ResponseEntity<>(newMovimiento, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDTO> obtenerMovimientoPorId(@PathVariable Long id) {
        Optional<MovimientoDTO> movimiento = movimientoService.obtenerMovimientoPorId(id);
        if (movimiento.isEmpty()){
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(movimiento.get(), HttpStatus.OK);
    }

    @GetMapping("/por/{numeroDecuenta}")
    public ResponseEntity<List<MovimientoDTO>> obtenerMovimientosPorNumeroDeCuenta(@PathVariable Long numeroDecuenta) {
        List<MovimientoDTO> movimientos = movimientoService.obtenerMovimientosPorNumeroDeCuenta(numeroDecuenta);
        return new ResponseEntity<>(movimientos, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> getAllMovimientos() {
        List<MovimientoDTO> movimientos = movimientoService.obtenerTodosLosMovimientos();
        return new ResponseEntity<>(movimientos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimiento(@PathVariable Long id) {
        Optional<MovimientoDTO> movimiento = movimientoService.obtenerMovimientoPorId(id);
        if (movimiento.isEmpty()){
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        movimientoService.eliminarMovimiento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

