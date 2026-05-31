package com.barbearia.repository;

import com.barbearia.model.Agendamento;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoRepository {
    private List<Agendamento> fonteDados = new ArrayList<>();

    public void criar(Agendamento agendamento){
        fonteDados.add(agendamento);
    }

    public Agendamento buscarPorId(Long id){
        for(Agendamento a : fonteDados){
            if(a.getId().equals(id)){
                return a;
            }
        }
        return null; 
    }

    public void atualizar(Long id, Agendamento agendamentoAtualizado){
        Agendamento a = buscarPorId(id); 
        if (a != null){ 
            a.setNomeCliente(agendamentoAtualizado.getNomeCliente());
            a.setContatoCliente(agendamentoAtualizado.getContatoCliente());
            a.setDataHora(agendamentoAtualizado.getDataHora());
            a.setStatus(agendamentoAtualizado.getStatus());
            a.setIdBarbeiro(agendamentoAtualizado.getIdBarbeiro());
            a.setIdServico(agendamentoAtualizado.getIdServico());
        }
    }
    
    public void cancelar(Long id){
        Agendamento a = buscarPorId(id); 
        if (a != null){
            a.setStatus("Cancelado");
        }
    }

    public List<Agendamento> listarTodos(){
        return new ArrayList<>(fonteDados);
    }

    public List<Agendamento> listarPorData(LocalDate data){
        List<Agendamento> filtrados = new ArrayList<>(); 
        for(Agendamento a : fonteDados){
            if(a.getDataHora().toLocalDate().equals(data)){
                filtrados.add(a); 
            }
        }
        return filtrados; 
    }

    public List<Agendamento> listarPorBarbeiro(Long idBarbeiro){
        List<Agendamento> filtrados = new ArrayList<>();
        for(Agendamento a : fonteDados){
            if(a.getIdBarbeiro().equals(idBarbeiro)){
                filtrados.add(a);
            }
        }
        return filtrados; 
    }

    public boolean validarDisponibilidade(LocalDateTime dataHora, Long idBarbeiro){
        for (Agendamento a : fonteDados){

            if (a.getIdBarbeiro().equals(idBarbeiro) && a.getDataHora().equals(dataHora) && !a.getStatus().equals("Cancelado")){
                return false;
            }
        }
        return true;
    }
}