package com.crm.cliente.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.cliente.entity.Cliente; // Não esqueça deste import

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
    // Adicione esta linha abaixo:
    Optional<Cliente> findByTelefone(String telefone);

    // Isolamento por usuário: só retorna/afeta clientes do usuário logado.
    // "AtivoTrue" = ignora clientes que foram soft-deletados (excluídos pela interface).
    List<Cliente> findByUsuarioIdAndAtivoTrue(Long usuarioId);
    Optional<Cliente> findByIdAndUsuarioIdAndAtivoTrue(Integer id, Long usuarioId);
    Optional<Cliente> findByTelefoneAndUsuarioIdAndAtivoTrue(String telefone, Long usuarioId);
}