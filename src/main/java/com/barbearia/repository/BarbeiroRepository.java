package com.barbearia.repository;

import com.barbearia.database.DatabaseConnection;
import com.barbearia.model.Barbeiro;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BarbeiroRepository {

    public void criar(Barbeiro barbeiro) throws SQLException {
        String sql = "INSERT INTO barbeiro (nome, login, senha, horarioInicio, horarioFim, ativo) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, barbeiro.getNome());
            stmt.setString(2, barbeiro.getLogin());
            stmt.setString(3, barbeiro.getSenha());
            stmt.setString(4, barbeiro.getHorarioInicio().toString());
            stmt.setString(5, barbeiro.getHorarioFim().toString());
            stmt.setInt(6, barbeiro.isAtivo() ? 1 : 0);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) barbeiro.setId(rs.getLong(1));
        }
    }

    public Barbeiro buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM barbeiro WHERE id = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapear(rs);
        }
        return null;
    }

    public Barbeiro buscarPorLogin(String login) throws SQLException {
        String sql = "SELECT * FROM barbeiro WHERE login = ? AND ativo = 1";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapear(rs);
        }
        return null;
    }

    public void atualizar(Long id, Barbeiro barbeiro) throws SQLException {
        String sql = "UPDATE barbeiro SET nome = ?, login = ?, senha = ?, horarioInicio = ?, horarioFim = ? WHERE id = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, barbeiro.getNome());
            stmt.setString(2, barbeiro.getLogin());
            stmt.setString(3, barbeiro.getSenha());
            stmt.setString(4, barbeiro.getHorarioInicio().toString());
            stmt.setString(5, barbeiro.getHorarioFim().toString());
            stmt.setLong(6, id);
            stmt.executeUpdate();
        }
    }

    public void desativar(Long id) throws SQLException {
        String sql = "UPDATE barbeiro SET ativo = 0 WHERE id = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Barbeiro> listarTodos() throws SQLException {
        List<Barbeiro> lista = new ArrayList<>();
        String sql = "SELECT * FROM barbeiro";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    private Barbeiro mapear(ResultSet rs) throws SQLException {
        return new Barbeiro(
            rs.getLong("id"),
            rs.getString("nome"),
            rs.getString("login"),
            rs.getString("senha"),
            LocalTime.parse(rs.getString("horarioInicio")),
            LocalTime.parse(rs.getString("horarioFim")),
            rs.getInt("ativo") == 1
        );
    }
}