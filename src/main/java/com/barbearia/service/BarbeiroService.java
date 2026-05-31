package com.barbearia.service;

import com.barbearia.model.Barbeiro;
import com.barbearia.repository.BarbeiroRepository;

import java.sql.SQLException;
import java.util.List;

public class BarbeiroService {

    private BarbeiroRepository barbeiroRepository = new BarbeiroRepository();

    public void criar(Barbeiro barbeiro) throws SQLException {
        List<Barbeiro> todos = barbeiroRepository.listarTodos();
        for (Barbeiro b : todos) {
            if (b.getLogin().equals(barbeiro.getLogin())) {
                throw new IllegalArgumentException("Já existe um barbeiro com esse login.");
            }
        }
        barbeiroRepository.criar(barbeiro);
    }

    public Barbeiro buscarPorId(Long id) throws SQLException {
        Barbeiro barbeiro = barbeiroRepository.buscarPorId(id);
        if (barbeiro == null) {
            throw new IllegalArgumentException("Barbeiro não encontrado.");
        }
        return barbeiro;
    }

    public Barbeiro autenticar(String login, String senha) throws SQLException {
        Barbeiro barbeiro = barbeiroRepository.buscarPorLogin(login);
        if (barbeiro == null || !barbeiro.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Login ou senha inválidos.");
        }
        return barbeiro;
    }

    public void atualizar(Long id, Barbeiro barbeiro) throws SQLException {
        buscarPorId(id);
        barbeiroRepository.atualizar(id, barbeiro);
    }

    public void desativar(Long id) throws SQLException {
        List<Barbeiro> ativos = barbeiroRepository.listarTodos()
            .stream()
            .filter(Barbeiro::isAtivo)
            .toList();
        if (ativos.size() <= 1) {
            throw new IllegalStateException("O sistema deve ter ao menos 1 barbeiro ativo.");
        }
        barbeiroRepository.desativar(id);
    }

    public List<Barbeiro> listarTodos() throws SQLException {
        return barbeiroRepository.listarTodos();
    }
}