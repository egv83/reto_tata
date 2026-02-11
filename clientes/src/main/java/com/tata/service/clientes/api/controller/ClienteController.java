package com.tata.service.clientes.api.controller;

import com.tata.service.clientes.application.dto.ClienteRequestDTO;
import com.tata.service.clientes.application.dto.ClienteResponseDTO;
import com.tata.service.clientes.application.dto.ClienteUpdateDTO;
import com.tata.service.clientes.application.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crear(
            @Valid @RequestBody ClienteRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.crearCliente(request));
    }

    @GetMapping
    public List<ClienteResponseDTO> listarClientes() {
        return clienteService.getClientes();
    }

    @GetMapping("/{id}")
    public ClienteResponseDTO obtenerCliente(
            @PathVariable Long id
    ) {
        return clienteService.getClienteId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(
            @PathVariable Long id,
            @RequestBody ClienteUpdateDTO request
    ) {
        clienteService.actualizarPUT(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> actualizarPartes(
            @PathVariable Long id,
            @RequestBody ClienteRequestDTO request
    ) {
        clienteService.actualizarPATCH(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrar(
            @PathVariable Long id
    ) {
//        clienteService.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(clienteService.eliminar(id));
    }

}
