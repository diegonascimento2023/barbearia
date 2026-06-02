package com.barbearia.controller;

import com.barbearia.model.Barbeiro;
import com.barbearia.service.BarbeiroService;

import java.sql.SQLException;
import java.util.List;

public class BarbeiroController {

    private final BarbeiroService barbeiroService;

    public BarbeiroController() {
        this(new BarbeiroService());
    }

    public BarbeiroController(BarbeiroService barbeiroService) {
        this.barbeiroService = barbeiroService;
    }

    public void criar(Barbeiro barbeiro) throws SQLException {
        barbeiroService.criar(barbeiro);
    }

    public Barbeiro buscarPorId(Long id) throws SQLException {
        return barbeiroService.buscarPorId(id);
    }

    public Barbeiro autenticar(String login, String senha) throws SQLException {
        return barbeiroService.autenticar(login, senha);
    }

    public void atualizar(Long id, Barbeiro barbeiro) throws SQLException {
        barbeiroService.atualizar(id, barbeiro);
    }

    public void desativar(Long id) throws SQLException {
        barbeiroService.desativar(id);
    }

    public List<Barbeiro> listarTodos() throws SQLException {
        return barbeiroService.listarTodos();
    }
}
