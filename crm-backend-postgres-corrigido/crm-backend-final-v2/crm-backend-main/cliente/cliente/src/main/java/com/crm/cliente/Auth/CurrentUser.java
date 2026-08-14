package com.crm.cliente.Auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.crm.cliente.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

/**
 * Resolve o usuário autenticado (a partir do e-mail colocado no SecurityContext
 * pelo JwtAuthFilter) para o seu id. Usado pelos services para escopar os dados
 * de cada usuário (isolamento multi-tenant).
 */
@Component
@RequiredArgsConstructor
public class CurrentUser {

    private final UsuarioRepository usuarioRepository;

    public Long id() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário autenticado não encontrado"))
                .getId();
    }
}
