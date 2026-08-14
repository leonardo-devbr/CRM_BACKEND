package com.crm.cliente.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crm.cliente.Auth.CurrentUser;
import com.crm.cliente.dto.OportunidadeRequest;
import com.crm.cliente.entity.Cliente;
import com.crm.cliente.entity.EtapaVenda;
import com.crm.cliente.entity.Oportunidade;
import com.crm.cliente.repository.ClienteRepository;
import com.crm.cliente.repository.OportunidadeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OportunidadeService {

    private final OportunidadeRepository oportunidadeRepository;
    private final ClienteRepository clienteRepository;
    private final CurrentUser currentUser;

    private Cliente clienteDoUsuarioOuFalha(Integer clienteId) {
        return clienteRepository.findByIdAndUsuarioIdAndAtivoTrue(clienteId, currentUser.id())
                .orElseThrow(() -> new RuntimeException(
                        "Cliente não encontrado: id=" + clienteId));
    }

    public Oportunidade criar(OportunidadeRequest request) {
        Cliente cliente = clienteDoUsuarioOuFalha(request.clienteId());

        Oportunidade oportunidade = new Oportunidade();
        oportunidade.setTitulo(request.titulo());
        oportunidade.setValorEstimado(request.valorEstimado());
        oportunidade.setEtapa(request.etapa());
        oportunidade.setCliente(cliente);

        return oportunidadeRepository.save(oportunidade);
    }

    public List<Oportunidade> ListarTodas() {
        return oportunidadeRepository.findByCliente_UsuarioIdAndCliente_AtivoTrue(currentUser.id());
    }

    public Oportunidade buscarPorId(Long id) {
        Oportunidade oportunidade = oportunidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Oportunidade não encontrada"));

        if (!oportunidade.getCliente().getUsuarioId().equals(currentUser.id())
                || !oportunidade.getCliente().isAtivo()) {
            // Mesma mensagem do "não encontrada" para não revelar que o registro existe.
            throw new RuntimeException("Oportunidade não encontrada");
        }
        return oportunidade;
    }

    public List<Oportunidade> listarPorEtapa(EtapaVenda etapa) {
        return oportunidadeRepository.findByEtapaAndCliente_UsuarioIdAndCliente_AtivoTrue(etapa, currentUser.id());
    }

    public List<Oportunidade> listarPorClienteId(Integer clienteId) {
        return oportunidadeRepository.findByClienteIdAndCliente_UsuarioIdAndCliente_AtivoTrue(clienteId, currentUser.id());
    }

    public Oportunidade atualizar(Long id, OportunidadeRequest request) {
        Oportunidade oportunidade = buscarPorId(id);
        Cliente cliente = clienteDoUsuarioOuFalha(request.clienteId());

        oportunidade.setTitulo(request.titulo());
        oportunidade.setValorEstimado(request.valorEstimado());
        oportunidade.setEtapa(request.etapa());
        oportunidade.setCliente(cliente);

        return oportunidadeRepository.save(oportunidade);
    }

    public void deletar(Long id) {
        Oportunidade oportunidade = buscarPorId(id);
        oportunidadeRepository.deleteById(oportunidade.getId());
    }
}
