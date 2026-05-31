package com.barbearia.repository;

import com.barbearia.model.Servico;
import java.util.ArrayList;
import java.util.List; 

public class ServicoRepository {
    
    private List<Servico> fonteDados = new ArrayList<>();

    public void criar(Servico servico){
        fonteDados.add(servico);
    }

    public Servico buscarPorId(Long id){
        for(Servico s: fonteDados){
            if(s.getId().equals(id)){
                return s;
            }
        }
        return null;
    }

    public void atualizar(Long id, Servico servicoAtualizado) {
        Servico s = buscarPorId(id);
        if (s != null) { 
            s.setNome(servicoAtualizado.getNome());
            s.setDescricao(servicoAtualizado.getDescricao());
            s.setPreco(servicoAtualizado.getPreco());
            s.setDuracaoEmMinutos(servicoAtualizado.getDuracaoEmMinutos());
        }
    }

    public void remover(Long id) {
        Servico s = buscarPorId(id);
        if (s != null) {
            fonteDados.remove(s); 
        }
    }

    public List<Servico> listarTodos(){
        return new ArrayList<>(fonteDados);
    }
}