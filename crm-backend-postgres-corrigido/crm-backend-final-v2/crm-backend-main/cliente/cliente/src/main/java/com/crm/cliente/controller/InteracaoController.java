package com.crm.cliente.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.cliente.entity.Interacao;
import com.crm.cliente.service.InteracaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Interação", description = "API para registro de interações com clientes")
@RestController
@RequestMapping("/api/clientes/{clienteId}/interacoes")
@RequiredArgsConstructor
public class InteracaoController {

    private final InteracaoService interacaoService;

    @Operation(summary = "Lista interações de um cliente")
    @GetMapping
    public ResponseEntity<List<Interacao>> listar(@PathVariable Integer clienteId) {
        return ResponseEntity.ok(interacaoService.listarPorCliente(clienteId));
    }

    @Operation(summary = "Registra uma nova interação")
    @PostMapping
    public ResponseEntity<Interacao> salvar(
            @PathVariable Integer clienteId,
            @Valid @RequestBody Interacao interacao) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(interacaoService.salvar(clienteId, interacao));
    }
}
