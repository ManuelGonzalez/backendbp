package com.devsu.backendbp.services;

import com.devsu.backendbp.entity.Cliente;
import com.devsu.backendbp.entity.dto.ClienteDTO;
import com.devsu.backendbp.repository.ClienteRepository;
import com.devsu.backendbp.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    public ClienteServiceTest() {
        MockitoAnnotations.openMocks(this);
    }


    private Cliente cliente1;
    private Cliente cliente2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente1 = new Cliente();
        cliente1.setClienteId(1L);
        cliente1.setContrasena("contrasena1");
        cliente1.setEstado(true);

        cliente2 = new Cliente();
        cliente2.setClienteId(2L);
        cliente2.setContrasena("contrasena2");
        cliente2.setEstado(false);

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente1));
        when(clienteRepository.findById(2L)).thenReturn(Optional.of(cliente2));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente1);
    }

    @Test
    public void testObtenerTodosLosClientes() {

        List<ClienteDTO> clientes = clienteService.obtenerTodosLosClientes();

        assertEquals(2, clientes.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    public void testEliminarCliente() {
        Long id = 1L;

        clienteService.eliminarCliente(id);

        verify(clienteRepository, times(1)).deleteById(id);
    }

    @Test
    public void testActualizarClienteNotFound() {
        Long id = 1L;
        ClienteDTO clienteDTO = new ClienteDTO();
        when(clienteRepository.existsById(id)).thenReturn(false);

        ClienteDTO updatedCliente = clienteService.actualizarCliente(id, clienteDTO);

        assertEquals(null, updatedCliente);
        verify(clienteRepository, times(0)).save(any(Cliente.class));
    }
}
