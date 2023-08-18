package com.devsu.backendbp.controller;

import com.devsu.backendbp.entity.dto.CuentaDTO;
import com.devsu.backendbp.service.CuentaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CuentaControllerTest {

    @InjectMocks
    private CuentaController cuentaController;

    @Mock
    private CuentaService cuentaService;

    @Test
    public void testCreateCuenta() {
        CuentaDTO cuentaDTO = new CuentaDTO();
        when(cuentaService.guardarCuenta(cuentaDTO)).thenReturn(cuentaDTO);

        ResponseEntity<CuentaDTO> response = cuentaController.createCuenta(cuentaDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cuentaDTO, response.getBody());
    }

    @Test
    public void testGetCuentaById() {
        Long id = 1L;
        CuentaDTO cuentaDTO = new CuentaDTO();
        when(cuentaService.obtenerCuentaPorId(id)).thenReturn(Optional.of(cuentaDTO));

        ResponseEntity<CuentaDTO> response = cuentaController.getCuentaById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cuentaDTO, response.getBody());
    }

    @Test
    public void testGetAllCuentas() {
        CuentaDTO cuenta1 = new CuentaDTO();
        CuentaDTO cuenta2 = new CuentaDTO();
        when(cuentaService.obtenerCuentas()).thenReturn(Arrays.asList(cuenta1, cuenta2));

        ResponseEntity<List<CuentaDTO>> response = cuentaController.getAllCuentas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testUpdateCuenta() {
        Long id = 1L;
        CuentaDTO cuentaDTO = new CuentaDTO();
        when(cuentaService.actualizarCuenta(id, cuentaDTO)).thenReturn(cuentaDTO);

        ResponseEntity<CuentaDTO> response = cuentaController.updateCuenta(id, cuentaDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cuentaDTO, response.getBody());
    }

    @Test
    public void testRetirarDinero() {
        Long id = 1L;
        Double monto = 500.0;
        CuentaDTO cuentaDTO = new CuentaDTO();
        when(cuentaService.retirarDinero(id, monto)).thenReturn(cuentaDTO);

        ResponseEntity<CuentaDTO> response = cuentaController.retirarDinero(id, monto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cuentaDTO, response.getBody());
    }

    @Test
    public void testDeleteCuenta() {
        Long id = 1L;
        when(cuentaService.obtenerCuentaPorId(id)).thenReturn(Optional.of(new CuentaDTO()));

        ResponseEntity<Void> response = cuentaController.deleteCuenta(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(cuentaService, times(1)).eliminarCuenta(id);
    }

}
