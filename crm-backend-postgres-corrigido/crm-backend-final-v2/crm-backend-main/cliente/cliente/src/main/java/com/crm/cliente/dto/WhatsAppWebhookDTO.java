package com.crm.cliente.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhatsAppWebhookDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String telefone;

    private String mensagem;
    private String email;
}