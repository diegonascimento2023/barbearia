package com.barbearia.model;

import java.time.LocalTime;

public class Barbeiro {
    
    private Long id; 
    private String nome;
    private String login; 
    private String senha; 
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private boolean ativo;  

    public Barbeiro(long id, String nome, String login, String senha, LocalTime horarioInicio, LocalTime horarioFim, boolean ativo){
        this.id = id; 
        this.nome = nome; 
        this.login = login;
        this.senha = senha; 
        this.horarioInicio = horarioInicio; 
        this.horarioFim = horarioFim; 
        this.ativo = true;
    }

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getNome(){return nome;}
    public void setNome(String nome){this.nome = nome;}

    public String getLogin(){return login;}
    public void setLogin(String login){this.login = login;}

    public String getSenha(){return senha;}
    public void setSenha(String senha){this.senha = senha;}

    public LocalTime getHorarioInicio(){return horarioInicio;}
    public void setHorarioInicio(LocalTime horarioInicio){this.horarioInicio = horarioInicio;}

    public LocalTime getHorarioFim(){return horarioFim;}
    public void setHorarioFim(LocalTime horarioFim){this.horarioFim = horarioFim;}

    public boolean getAtivo(){return ativo;}
    public void setAtivo(boolean ativo){this.ativo = ativo;}
}
