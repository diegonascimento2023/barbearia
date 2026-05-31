package com.barbearia.repository;

import com.barbearia.database.DatabaseConnection;
import com.barbearia.model.Agendamento;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoRepository {

    public void criar(Agendamento agendamento) throws SQLException {
        String sql = "INSERT INTO agendamento (nomeCliente, contatoCliente, dataHora, status, idBarbeiro, idServico) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, agendamento.getNomeCliente());
            stmt.setString(2, agendamento.getContatoCliente());
            stmt.setString(3, agendamento.getDataHora().toString());
            stmt.setString(4, agendamento.getStatus());
            stmt.setLong(5, agendamento.getIdBarbeiro());
            stmt.setLong(6, agendamento.getIdServico());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) agendamento.setId(rs.getLong(1));
        }
    }

    public Agendamento buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM agendamento WHERE id = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapear(rs);
        }
        return null;
    }

    public void atualizar(Long id, Agendamento agendamento) throws SQLException {
        String sql = "UPDATE agendamento SET nomeCliente = ?, contatoCliente = ?, dataHora = ?, status = ?, idBarbeiro = ?, idServico = ? WHERE id = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, agendamento.getNomeCliente());
            stmt.setString(2, agendamento.getContatoCliente());
            stmt.setString(3, agendamento.getDataHora().toString());
            stmt.setString(4, agendamento.getStatus());
            stmt.setLong(5, agendamento.getIdBarbeiro());
            stmt.setLong(6, agendamento.getIdServico());
            stmt.setLong(7, id);
            stmt.executeUpdate();
        }
    }

    public void cancelar(Long id) throws SQLException {
        String sql = "UPDATE agendamento SET status = 'cancelado' WHERE id = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public boolean validarDisponibilidade(LocalDateTime dataHora, Long idBarbeiro) throws SQLException {
        String sql = "SELECT COUNT(*) FROM agendamento WHERE idBarbeiro = ? AND dataHora = ? AND status != 'cancelado'";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, idBarbeiro);
            stmt.setString(2, dataHora.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1) == 0;
        }
        return false;
    }

    public List<Agendamento> listarTodos() throws SQLException {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM agendamento";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<Agendamento> listarPorData(LocalDate data) throws SQLException {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM agendamento WHERE DATE(dataHora) = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, data.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<Agendamento> listarPorBarbeiro(Long idBarbeiro) throws SQLException {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM agendamento WHERE idBarbeiro = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, idBarbeiro);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    private Agendamento mapear(ResultSet rs) throws SQLException {
        return new Agendamento(
            rs.getLong("id"),
            rs.getString("nomeCliente"),
            rs.getString("contatoCliente"),
            LocalDateTime.parse(rs.getString("dataHora")),
            rs.getString("status"),
            rs.getLong("idBarbeiro"),
            rs.getLong("idServico")
        );
    }
}