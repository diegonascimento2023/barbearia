package com.barbearia.repository;

import com.barbearia.database.DatabaseConnection;
import com.barbearia.model.Servico;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoRepository {

    public void criar(Servico servico) throws SQLException {
        String sql = "INSERT INTO servico (nome, descricao, preco, duracaoEmMinutos) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, servico.getNome());
            stmt.setString(2, servico.getDescricao());
            stmt.setBigDecimal(3, servico.getPreco());
            stmt.setInt(4, servico.getDuracaoEmMinutos());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) servico.setId(rs.getLong(1));
        }
    }

    public Servico buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM servico WHERE id = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapear(rs);
        }
        return null;
    }

    public void atualizar(Long id, Servico servico) throws SQLException {
        String sql = "UPDATE servico SET nome = ?, descricao = ?, preco = ?, duracaoEmMinutos = ? WHERE id = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, servico.getNome());
            stmt.setString(2, servico.getDescricao());
            stmt.setBigDecimal(3, servico.getPreco());
            stmt.setInt(4, servico.getDuracaoEmMinutos());
            stmt.setLong(5, id);
            stmt.executeUpdate();
        }
    }

    public void remover(Long id) throws SQLException {
        String sql = "DELETE FROM servico WHERE id = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Servico> listarTodos() throws SQLException {
        List<Servico> lista = new ArrayList<>();
        String sql = "SELECT * FROM servico";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    private Servico mapear(ResultSet rs) throws SQLException {
        return new Servico(
            rs.getLong("id"),
            rs.getString("nome"),
            rs.getString("descricao"),
            rs.getBigDecimal("preco"),
            rs.getInt("duracaoEmMinutos")
        );
    }
}