package com.crm.cliente.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.cliente.dto.OportunidadeRequest;
import com.crm.cliente.entity.EtapaVenda;
import com.crm.cliente.entity.Oportunidade;
import com.crm.cliente.service.OportunidadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Oportunidade", description = "API para gerenciamento de oportunidades de venda")
@RestController
@RequestMapping("/api/oportunidades")
@RequiredArgsConstructor
public class OportunidadeController {

    private final OportunidadeService oportunidadeService;

    @Operation(summary = "Cria uma nova oportunidade")
    @PostMapping
    public ResponseEntity<Oportunidade> criar(@Valid @RequestBody OportunidadeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(oportunidadeService.criar(request));
    }

    @Operation(summary = "Lista todas as oportunidades")
    @GetMapping
    public ResponseEntity<List<Oportunidade>> listarTodas() {
        return ResponseEntity.ok(oportunidadeService.ListarTodas());
    }

    @Operation(summary = "Busca oportunidade por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Oportunidade> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(oportunidadeService.buscarPorId(id));
    }

    @Operation(summary = "Lista oportunidades por etapa")
    @GetMapping("/etapa/{etapa}")
    public ResponseEntity<List<Oportunidade>> listarPorEtapa(@PathVariable EtapaVenda etapa) {
        return ResponseEntity.ok(oportunidadeService.listarPorEtapa(etapa));
    }

    @Operation(summary = "Lista oportunidades por cliente")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Oportunidade>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(oportunidadeService.listarPorClienteId(clienteId));
    }

    @Operation(summary = "Atualiza uma oportunidade")
    @PutMapping("/{id}")
    public ResponseEntity<Oportunidade> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody OportunidadeRequest request) {
        return ResponseEntity.ok(oportunidadeService.atualizar(id, request));
    }

    @Operation(summary = "Deleta uma oportunidade")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        oportunidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
