package com.crm.cliente.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    private String endereco;

    private String mensagem;

    // Dono do registro: id do Usuario que criou este cliente.
    // Usado para isolar os dados entre usuários (cada um só vê os seus).
    private Long usuarioId;

    // Soft delete: ao "excluir" pela interface, isso vira false em vez de
    // apagar a linha do banco. Todo o histórico (interações, oportunidades)
    // continua intacto, só deixa de aparecer nas listagens.
    private boolean ativo = true;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Interacao> interacoes = new ArrayList<>();
}
