package com.barbearia.model;

import java.util.ArrayList; 
import java.util.List; 

public class Barbearia {
    private String nomeEstabelecimento;
    private List<Barbeiro> barbeiros; 
    private List<Servico> servicos; 
    private List<Agendamento> agendamentos; 

    public Barbearia(String nomeEstabelecimento){
        this.nomeEstabelecimento = nomeEstabelecimento; 
        this.barbeiros = new ArrayList<>();
        this.servicos = new ArrayList<>();
        this.agendamentos = new ArrayList<>();
    }

    public String getNomeEstabelecimento(){return nomeEstabelecimento;}
    public void setNomeEstabelecimento(String nomeEstabelecimento){this.nomeEstabelecimento = nomeEstabelecimento;}
 
    public void abrirEstabelecimento(){
        System.out.println("A barbearia "+nomeEstabelecimento+" está aberta para atendimentos.");
    }
}