package com.crm.cliente.dto;

import java.math.BigDecimal;

import com.crm.cliente.entity.EtapaVenda;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OportunidadeRequest(

    @NotBlank(message = "Título é obrigatório")
    String titulo,

    @NotNull(message = "Valor estimado é obrigatório")
    @Positive(message = "Valor estimado deve ser positivo")
    BigDecimal valorEstimado,

    @NotNull(message = "Etapa é obrigatória")
    EtapaVenda etapa,

    @NotNull(message = "Cliente é obrigatório")
    Integer clienteId

) {}
