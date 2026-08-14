package com.crm.cliente.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crm.cliente.Auth.CurrentUser;
import com.crm.cliente.entity.Cliente;
import com.crm.cliente.entity.Interacao;
import com.crm.cliente.repository.ClienteRepository;
import com.crm.cliente.repository.InteracaoRepository;

@Service
public class InteracaoService {

    private final InteracaoRepository interacaoRepository;
    private final ClienteRepository clienteRepository;
    private final CurrentUser currentUser;

    public InteracaoService(InteracaoRepository interacaoRepository, ClienteRepository clienteRepository, CurrentUser currentUser) {
        this.interacaoRepository = interacaoRepository;
        this.clienteRepository = clienteRepository;
        this.currentUser = currentUser;
    }

    private Cliente clienteDoUsuarioOuFalha(Integer clienteId) {
        return clienteRepository.findByIdAndUsuarioIdAndAtivoTrue(clienteId, currentUser.id())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + clienteId));
    }

    // Ajustado para Integer para bater com sua Entity Cliente
    public List<Interacao> listarPorCliente(Integer clienteId) {
        clienteDoUsuarioOuFalha(clienteId); // valida que o cliente é do usuário logado
        return interacaoRepository.findByClienteId(clienteId);
    }

    public Interacao salvar(Integer clienteId, Interacao interacao) {
        Cliente cliente = clienteDoUsuarioOuFalha(clienteId);

        interacao.setCliente(cliente); // Vincula o cliente encontrado à interação
        return interacaoRepository.save(interacao);
    }
}
