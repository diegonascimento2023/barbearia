package com.barbearia.controller;

import com.barbearia.model.Agendamento;
import com.barbearia.service.AgendamentoService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController() {
        this(new AgendamentoService());
    }

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    public void criar(Agendamento agendamento) throws SQLException {
        agendamentoService.criar(agendamento);
    }

    public Agendamento buscarPorId(Long id) throws SQLException {
        return agendamentoService.buscarPorId(id);
    }

    public void atualizar(Long id, Agendamento agendamento) throws SQLException {
        agendamentoService.atualizar(id, agendamento);
    }

    public void cancelar(Long id) throws SQLException {
        agendamentoService.cancelar(id);
    }

    public boolean validarDisponibilidade(LocalDateTime dataHora, Long idBarbeiro) throws SQLException {
        return agendamentoService.validarDisponibilidade(dataHora, idBarbeiro);
    }

    public List<Agendamento> listarTodos() throws SQLException {
        return agendamentoService.listarTodos();
    }

    public List<Agendamento> listarPorData(LocalDate data) throws SQLException {
        return agendamentoService.listarPorData(data);
    }

    public List<Agendamento> listarPorBarbeiro(Long idBarbeiro) throws SQLException {
        return agendamentoService.listarPorBarbeiro(idBarbeiro);
    }
}
