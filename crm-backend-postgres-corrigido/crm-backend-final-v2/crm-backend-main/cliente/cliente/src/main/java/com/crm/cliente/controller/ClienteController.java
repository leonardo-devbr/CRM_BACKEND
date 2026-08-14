package com.crm.cliente.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.cliente.entity.Cliente;
import com.crm.cliente.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Cliente", description = "API para gerenciamento de clientes")
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Lista todos os clientes")
    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @Operation(summary = "Cria um novo cliente")
    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@Valid @RequestBody Cliente cliente) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.salvar(cliente));
    }

    @Operation(summary = "Busca um cliente pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscar(@PathVariable Integer id) {
        return clienteService.BuscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deleta um cliente pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        boolean removido = clienteService.deletar(id);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Deleta todos os clientes")
    @DeleteMapping("/deletar-todos")
    public ResponseEntity<Void> deletarTodos() {
        clienteService.deletarTodos();
        return ResponseEntity.noContent().build();
    }
}
