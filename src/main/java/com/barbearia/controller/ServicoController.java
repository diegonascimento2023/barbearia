package com.barbearia.controller;

import com.barbearia.model.Servico;
import com.barbearia.service.ServicoService;

import java.sql.SQLException;
import java.util.List;

public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController() {
        this(new ServicoService());
    }

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    public void criar(Servico servico) throws SQLException {
        servicoService.criar(servico);
    }

    public Servico buscarPorId(Long id) throws SQLException {
        return servicoService.buscarPorId(id);
    }

    public void atualizar(Long id, Servico servico) throws SQLException {
        servicoService.atualizar(id, servico);
    }

    public void remover(Long id) throws SQLException {
        servicoService.remover(id);
    }

    public List<Servico> listarTodos() throws SQLException {
        return servicoService.listarTodos();
    }
}
