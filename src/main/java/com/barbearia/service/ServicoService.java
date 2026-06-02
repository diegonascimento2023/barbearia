package com.barbearia.service;

import com.barbearia.model.Servico;
import com.barbearia.repository.ServicoRepository;

import java.sql.SQLException;
import java.util.List;

public class ServicoService {

    private ServicoRepository servicoRepository = new ServicoRepository();

    public void criar(Servico servico) throws SQLException {
        servicoRepository.criar(servico);
    }

    public Servico buscarPorId(Long id) throws SQLException {
        Servico servico = servicoRepository.buscarPorId(id);
        if (servico == null) {
            throw new IllegalArgumentException("Serviço não encontrado.");
        }
        return servico;
    }

    public void atualizar(Long id, Servico servico) throws SQLException {
        buscarPorId(id);
        servicoRepository.atualizar(id, servico);
    }

    public void remover(Long id) throws SQLException {
        buscarPorId(id);
        servicoRepository.remover(id);
    }

    public List<Servico> listarTodos() throws SQLException {
        return servicoRepository.listarTodos();
    }
}