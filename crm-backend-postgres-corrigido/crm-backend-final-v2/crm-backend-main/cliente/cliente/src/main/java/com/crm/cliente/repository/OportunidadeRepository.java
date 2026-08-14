package com.crm.cliente.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.cliente.entity.EtapaVenda;
import com.crm.cliente.entity.Oportunidade;

@Repository
public interface OportunidadeRepository extends JpaRepository<Oportunidade, Long> {
    List<Oportunidade> findByEtapa(EtapaVenda etapa);
    // Corrigido: Cliente.id é Integer (int), não Long. O tipo divergente aqui
    // causava falha de binding do Hibernate ao executar a query derivada.
    List<Oportunidade> findByClienteId(Integer clienteId);

    // Isolamento por usuário: navega Oportunidade -> Cliente -> usuarioId,
    // já que a oportunidade não tem dono próprio, herda do cliente.
    // AtivoTrue: some da lista se o cliente foi "excluído" (soft delete) pela interface.
    List<Oportunidade> findByCliente_UsuarioIdAndCliente_AtivoTrue(Long usuarioId);
    List<Oportunidade> findByEtapaAndCliente_UsuarioIdAndCliente_AtivoTrue(EtapaVenda etapa, Long usuarioId);
    List<Oportunidade> findByClienteIdAndCliente_UsuarioIdAndCliente_AtivoTrue(Integer clienteId, Long usuarioId);
}

