package com.devsu.backendbp.controller;

import com.devsu.backendbp.entity.dto.CuentaDTO;
import com.devsu.backendbp.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas")
@Validated
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @PostMapping
    public ResponseEntity<CuentaDTO> createCuenta(@Valid @RequestBody CuentaDTO cuentaDTO) {
        CuentaDTO newCuenta = cuentaService.guardarCuenta(cuentaDTO);
        return new ResponseEntity<>(newCuenta, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> getCuentaById(@PathVariable Long id) {
        Optional<CuentaDTO> cuenta = cuentaService.obtenerCuentaPorId(id);
        if (cuenta.isEmpty()){
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cuenta.get(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CuentaDTO>> getAllCuentas() {
        List<CuentaDTO> cuentas = cuentaService.obtenerCuentas();
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaDTO> updateCuenta(@PathVariable Long id, @Valid @RequestBody CuentaDTO cuentaDTO) {
        CuentaDTO updatedCuenta = cuentaService.actualizarCuenta(id, cuentaDTO);
        return new ResponseEntity<>(updatedCuenta, HttpStatus.OK);
    }

    @PatchMapping("/{id}/retirar/{monto}")
    public ResponseEntity<CuentaDTO> retirarDinero(@PathVariable Long id, @PathVariable Double monto) {
        CuentaDTO response = cuentaService.retirarDinero(id, monto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        Optional<CuentaDTO> cuenta = cuentaService.obtenerCuentaPorId(id);
        if (cuenta.isEmpty()){
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        cuentaService.eliminarCuenta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

