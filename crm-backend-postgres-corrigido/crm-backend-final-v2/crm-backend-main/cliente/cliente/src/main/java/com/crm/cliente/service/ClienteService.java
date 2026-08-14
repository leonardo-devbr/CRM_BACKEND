package com.crm.cliente.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.cliente.Auth.CurrentUser;
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

    @Autowired
    private CurrentUser currentUser;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        Long usuarioId = currentUser.id();

        // Isolado por usuário: só considera "já existe" se o telefone já
        // pertencer a um cliente ativo DESTE usuário.
        return clienteRepository.findByTelefoneAndUsuarioIdAndAtivoTrue(cliente.getTelefone(), usuarioId)
            .map(clienteExistente -> {
                clienteExistente.setNome(cliente.getNome());
                clienteExistente.setMensagem(cliente.getMensagem());
                if(cliente.getEmail() != null) clienteExistente.setEmail(cliente.getEmail());
                
                Cliente atualizado = clienteRepository.save(clienteExistente);
                registrarInteracao(atualizado, cliente.getMensagem());
                return atualizado;
            })
            .orElseGet(() -> {
                cliente.setUsuarioId(usuarioId);
                cliente.setAtivo(true);
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

    public List<Cliente> listarTodos() {
        return clienteRepository.findByUsuarioIdAndAtivoTrue(currentUser.id());
    }

    public Optional<Cliente> BuscarPorId(Integer id) {
        return clienteRepository.findByIdAndUsuarioIdAndAtivoTrue(id, currentUser.id());
    }

    // Soft delete: em vez de apagar a linha (o que quebrava com FK de
    // Oportunidade e causava o 500), só marca ativo=false. O histórico
    // (interações, oportunidades) continua intacto e o cliente some das
    // listagens porque elas já filtram por AtivoTrue.
    // Retorna false se o cliente não existe ou não pertence ao usuário logado.
    @Transactional
    public boolean deletar(Integer id) {
        return clienteRepository.findByIdAndUsuarioIdAndAtivoTrue(id, currentUser.id())
            .map(cliente -> {
                cliente.setAtivo(false);
                clienteRepository.save(cliente);
                return true;
            })
            .orElse(false);
    }

    @Transactional
    public void deletarTodos() {
        List<Cliente> clientes = clienteRepository.findByUsuarioIdAndAtivoTrue(currentUser.id());
        clientes.forEach(c -> c.setAtivo(false));
        clienteRepository.saveAll(clientes);
    }
}
