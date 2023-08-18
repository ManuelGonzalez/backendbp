package com.devsu.backendbp.controller;

import com.devsu.backendbp.entity.dto.MovimientoDTO;
import com.devsu.backendbp.service.MovimientoService;
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
public class MovimientoControllerTest {

    @InjectMocks
    private MovimientoController movimientoController;

    @Mock
    private MovimientoService movimientoService;

    @Test
    public void testCrearMovimiento() {
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        when(movimientoService.guardarMovimiento(movimientoDTO)).thenReturn(movimientoDTO);

        ResponseEntity<MovimientoDTO> response = movimientoController.crearMovimiento(movimientoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(movimientoDTO, response.getBody());
    }

    @Test
    public void testObtenerMovimientoPorId() {
        Long id = 1L;
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        when(movimientoService.obtenerMovimientoPorId(id)).thenReturn(Optional.of(movimientoDTO));

        ResponseEntity<MovimientoDTO> response = movimientoController.obtenerMovimientoPorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movimientoDTO, response.getBody());
    }

    @Test
    public void testGetAllMovimientos() {
        MovimientoDTO movimiento1 = new MovimientoDTO();
        MovimientoDTO movimiento2 = new MovimientoDTO();
        when(movimientoService.obtenerTodosLosMovimientos()).thenReturn(Arrays.asList(movimiento1, movimiento2));

        ResponseEntity<List<MovimientoDTO>> response = movimientoController.getAllMovimientos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testUpdateMovimiento() {
        Long id = 1L;
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        when(movimientoService.actualizarMovimiento(id, movimientoDTO)).thenReturn(movimientoDTO);

        ResponseEntity<MovimientoDTO> response = movimientoController.updateMovimiento(id, movimientoDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movimientoDTO, response.getBody());
    }

    @Test
    public void testDeleteMovimiento() {
        Long id = 1L;
        when(movimientoService.obtenerMovimientoPorId(id)).thenReturn(Optional.of(new MovimientoDTO()));

        ResponseEntity<Void> response = movimientoController.deleteMovimiento(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(movimientoService, times(1)).eliminarMovimiento(id);
    }

}
