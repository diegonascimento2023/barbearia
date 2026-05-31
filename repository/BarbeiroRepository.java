package com.barbearia.repository;

import com.barbearia.model.Barbeiro;
import java.util.ArrayList;
import java.util.List;

public class BarbeiroRepository {
    private List<Barbeiro> fonteDados = new ArrayList<>();

    public void criar(Barbeiro barbeiro){
        fonteDados.add(barbeiro);
    }

    public Barbeiro buscarPorId(Long id){
        for(Barbeiro b : fonteDados){
            if(b.getId().equals(id)){
                return b; 
            }
        }
        return null;
    }

    public void atualizar(Long id, Barbeiro barbeiroAtualizado){
        Barbeiro b = buscarPorId(id); 

        if (b != null){ 
            b.setNome(barbeiroAtualizado.getNome());
            b.setLogin(barbeiroAtualizado.getLogin());
            b.setSenha(barbeiroAtualizado.getSenha());
            b.setHorarioInicio(barbeiroAtualizado.getHorarioInicio());
            b.setHorarioFim(barbeiroAtualizado.getHorarioFim());
            b.setAtivo(barbeiroAtualizado.getAtivo());
        }   
    }

    public void desativar(Long id){
        Barbeiro b = buscarPorId(id); 
        if (b != null){
            b.setAtivo(false);
        }
    }

    public List<Barbeiro> listarTodos(){
        return new ArrayList<>(fonteDados);
    }
}