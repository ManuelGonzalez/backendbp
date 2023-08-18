package com.devsu.backendbp.service;

import com.devsu.backendbp.entity.Cliente;
import com.devsu.backendbp.entity.dto.ClienteDTO;
import com.devsu.backendbp.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> obtenerTodosLosClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ClienteDTO> obtenerClientePorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(this::convertToDTO);
    }

    public ClienteDTO guardarCliente(ClienteDTO clienteDto) {
        Cliente savedCliente = clienteRepository.save(convertToEntity(clienteDto));
        return convertToDTO(savedCliente);
    }

    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    public ClienteDTO actualizarCliente(Long id, ClienteDTO clienteActualizado) {
        if (clienteRepository.existsById(id)) {
            clienteActualizado.setId(id);
            Cliente updatedCliente = clienteRepository.save(convertToEntity(clienteActualizado));
            return convertToDTO(updatedCliente);
        }
        return null;
    }

    private ClienteDTO convertToDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setContrasena(cliente.getContrasena());
        clienteDTO.setEstado(cliente.getEstado());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setGenero(cliente.getGenero());
        clienteDTO.setEdad(cliente.getEdad());
        clienteDTO.setIdentificacion(cliente.getIdentificacion());
        clienteDTO.setDireccion(cliente.getDireccion());
        clienteDTO.setTelefono(cliente.getTelefono());
        clienteDTO.setId(cliente.getId());

        return clienteDTO;
    }

    private Cliente convertToEntity(ClienteDTO clienteDto) {
        Cliente cliente = new Cliente();
        cliente.setContrasena(clienteDto.getContrasena());
        cliente.setEstado(clienteDto.isEstado());
        cliente.setNombre(clienteDto.getNombre());
        cliente.setGenero(clienteDto.getGenero());
        cliente.setEdad(clienteDto.getEdad());
        cliente.setIdentificacion(clienteDto.getIdentificacion());
        cliente.setDireccion(clienteDto.getDireccion());
        cliente.setTelefono(clienteDto.getTelefono());

        return cliente;
    }
}
