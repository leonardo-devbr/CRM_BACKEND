package com.crm.cliente.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record RegisterRequest(

    @NotBlank(message = "O campo 'nome' não pode estar em branco") String nome,
    @NotBlank(message = "O campo 'email' não pode estar em branco") @Email(message = "O campo 'email' deve ser um endereço de e-mail válido") String email,
    @NotBlank(message = "O campo 'senha' não pode estar em branco") @Size(min = 6, max = 100, message = "O campo 'senha' deve ter entre 6 e 100 caracteres") String senha


)

    

{}