package com.crm.cliente.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.cliente.entity.Cliente;
import com.crm.cliente.entity.Interacao;
import com.crm.cliente.repository.ClienteRepository;
import com.crm.cliente.repository.InteracaoRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private InteracaoRepository interacaoRepository;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        return clienteRepository.findByTelefone(cliente.getTelefone())
            .map(clienteExistente -> {
                clienteExistente.setNome(cliente.getNome());
                clienteExistente.setMensagem(cliente.getMensagem());
                if(cliente.getEmail() != null) clienteExistente.setEmail(cliente.getEmail());
                
                Cliente atualizado = clienteRepository.save(clienteExistente);
                registrarInteracao(atualizado, cliente.getMensagem());
                return atualizado;
            })
            .orElseGet(() -> {
                Cliente novo = clienteRepository.save(cliente);
                registrarInteracao(novo, cliente.getMensagem());
                return novo;
            });
    }

    private void registrarInteracao(Cliente cliente, String conteudo) {
        Interacao interacao = new Interacao();
        interacao.setDescricao("Mensagem via WhatsApp: " + (conteudo != null ? conteudo : "Sem conteúdo"));
        interacao.setCliente(cliente);
        interacaoRepository.save(interacao);
    }

    public List<Cliente> listarTodos() { return clienteRepository.findAll(); }
    public Optional<Cliente> BuscarPorId(Integer id) { return clienteRepository.findById(id); }
    public void deletar(Integer id) { clienteRepository.deleteById(id); }
    public void deletarTodos() { clienteRepository.deleteAll(); }
}