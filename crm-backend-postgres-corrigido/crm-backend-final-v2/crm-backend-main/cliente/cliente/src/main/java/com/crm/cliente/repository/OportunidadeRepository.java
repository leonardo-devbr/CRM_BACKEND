package com.crm.cliente.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.cliente.entity.EtapaVenda;
import com.crm.cliente.entity.Oportunidade;

@Repository
public interface OportunidadeRepository extends JpaRepository<Oportunidade, Long> {
    List<Oportunidade> findByEtapa(EtapaVenda etapa);
    List<Oportunidade> findByClienteId(Long clienteId);
}
