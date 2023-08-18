package com.devsu.backendbp.integration;

import com.devsu.backendbp.entity.Cliente;
import com.devsu.backendbp.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void testCreateAndRetrieveCliente() throws Exception {
        Cliente newCliente = new Cliente();
        newCliente.setNombre("Juan");
        newCliente.setGenero("Masculino");
        newCliente.setEdad(30);
        newCliente.setIdentificacion("123456");
        newCliente.setDireccion("Calle 123");
        newCliente.setTelefono("3001234567");
        newCliente.setClienteId(1L);
        newCliente.setContrasena("password");
        newCliente.setEstado(true);

        // Serialize the request object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(newCliente);

        // Test POST request to create a new client
        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        // Test GET request to retrieve clients
        mockMvc.perform(get("/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
