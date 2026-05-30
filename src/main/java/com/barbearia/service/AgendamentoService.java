package com.barbearia.service;

import com.barbearia.model.Agendamento;
import com.barbearia.model.Barbeiro;
import com.barbearia.repository.AgendamentoRepository;
import com.barbearia.repository.BarbeiroRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AgendamentoService {

    private AgendamentoRepository agendamentoRepository = new AgendamentoRepository();
    private BarbeiroRepository barbeiroRepository = new BarbeiroRepository();

    public void criar(Agendamento agendamento) throws SQLException {
        Barbeiro barbeiro = barbeiroRepository.buscarPorId(agendamento.getIdBarbeiro());
        if (barbeiro == null || !barbeiro.isAtivo()) {
            throw new IllegalArgumentException("Barbeiro não encontrado ou inativo.");
        }
        LocalDateTime dataHora = agendamento.getDataHora();
        if (dataHora.toLocalTime().isBefore(barbeiro.getHorarioInicio()) ||
            dataHora.toLocalTime().isAfter(barbeiro.getHorarioFim())) {
            throw new IllegalArgumentException("Horário fora do expediente do barbeiro.");
        }
        if (!agendamentoRepository.validarDisponibilidade(dataHora, agendamento.getIdBarbeiro())) {
            throw new IllegalArgumentException("Já existe um agendamento para esse barbeiro nesse horário.");
        }
        agendamento.setStatus("pendente");
        agendamentoRepository.criar(agendamento);
    }

    public Agendamento buscarPorId(Long id) throws SQLException {
        Agendamento agendamento = agendamentoRepository.buscarPorId(id);
        if (agendamento == null) {
            throw new IllegalArgumentException("Agendamento não encontrado.");
        }
        return agendamento;
    }

    public void atualizar(Long id, Agendamento agendamento) throws SQLException {
        buscarPorId(id);
        agendamentoRepository.atualizar(id, agendamento);
    }

    public void cancelar(Long id) throws SQLException {
        Agendamento agendamento = buscarPorId(id);
        if (agendamento.getDataHora().minusHours(2).isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Cancelamento permitido somente com antecedência mínima de 2 horas.");
        }
        agendamentoRepository.cancelar(id);
    }

    public boolean validarDisponibilidade(LocalDateTime dataHora, Long idBarbeiro) throws SQLException {
        return agendamentoRepository.validarDisponibilidade(dataHora, idBarbeiro);
    }

    public List<Agendamento> listarTodos() throws SQLException {
        return agendamentoRepository.listarTodos();
    }

    public List<Agendamento> listarPorData(LocalDate data) throws SQLException {
        return agendamentoRepository.listarPorData(data);
    }

    public List<Agendamento> listarPorBarbeiro(Long idBarbeiro) throws SQLException {
        return agendamentoRepository.listarPorBarbeiro(idBarbeiro);
    }
}