package com.devsu.backendbp.controller;

import com.devsu.backendbp.entity.dto.ClienteDTO;
import com.devsu.backendbp.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @Test
    public void testObtenerClientes() {
        ClienteDTO cliente1 = new ClienteDTO();
        ClienteDTO cliente2 = new ClienteDTO();
        when(clienteService.obtenerTodosLosClientes()).thenReturn(Arrays.asList(cliente1, cliente2));

        List<ClienteDTO> result = clienteController.obtenerClientes();

        assertEquals(2, result.size());
        verify(clienteService, times(1)).obtenerTodosLosClientes();
    }

    @Test
    public void testCrearCliente() {
        ClienteDTO cliente = new ClienteDTO();
        when(clienteService.guardarCliente(cliente)).thenReturn(cliente);

        ResponseEntity<ClienteDTO> response = clienteController.crearCliente(cliente);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    @Test
    public void testActualizarCliente() {
        Long id = 1L;
        ClienteDTO clienteActualizado = new ClienteDTO();
        when(clienteService.actualizarCliente(id, clienteActualizado)).thenReturn(clienteActualizado);

        ResponseEntity<ClienteDTO> response = clienteController.actualizarCliente(id, clienteActualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteActualizado, response.getBody());
    }

    @Test
    public void testActualizarClienteNotFound() {
        Long id = 1L;
        ClienteDTO clienteActualizado = new ClienteDTO();
        when(clienteService.actualizarCliente(id, clienteActualizado)).thenReturn(null);

        ResponseEntity<ClienteDTO> response = clienteController.actualizarCliente(id, clienteActualizado);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testEliminarCliente() {
        Long id = 1L;

        ResponseEntity<Void> response = clienteController.eliminarCliente(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clienteService, times(1)).eliminarCliente(id);
    }
}
