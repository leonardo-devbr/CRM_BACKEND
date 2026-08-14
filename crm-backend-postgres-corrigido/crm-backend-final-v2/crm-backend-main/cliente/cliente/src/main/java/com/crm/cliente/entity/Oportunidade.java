package com.crm.cliente.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Oportunidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    @NotNull(message = "Valor estimado é obrigatório")
    @Positive(message = "Valor estimado deve ser positivo")
    private BigDecimal valorEstimado;

    @NotNull(message = "Etapa é obrigatória")
    @Enumerated(EnumType.STRING)
    private EtapaVenda etapa;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonBackReference  // Evita loop infinito com Cliente.interacoes
    private Cliente cliente;

    @CreatedDate
    private LocalDateTime dataCriacao;
}
