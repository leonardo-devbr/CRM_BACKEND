package com.crm.cliente.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.cliente.entity.Interacao;

public interface InteracaoRepository extends JpaRepository<Interacao, Integer> {
    // Mude de (Long clienteId) para (Integer clienteId)
    List<Interacao> findByClienteId(Integer clienteId); 
}