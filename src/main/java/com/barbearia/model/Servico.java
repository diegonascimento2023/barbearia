package com.barbearia.model;

import java.math.BigDecimal;

public class Servico {
    
    private Long id; 
    private String nome; 
    private String descricao; 
    private BigDecimal preco; 
    private int duracaoEmMinutos;
    
    public Servico(Long id, String nome, String descricao, BigDecimal preco, int duracaoEmMinutos){
        this.id = id;
        this.nome = nome; 
        this.descricao = descricao; 
        this.preco = preco; 
        this.duracaoEmMinutos = duracaoEmMinutos; 
    }

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}
    
    public String getNome(){return nome;}
    public void setNome(String nome){this.nome = nome;}
    
    public String getDescricao(){return descricao;}
    public void setDescricao(String descricao){this.descricao = descricao;}
    
    public BigDecimal getPreco(){return preco;}
    public void setPreco(BigDecimal preco){this.preco = preco;}

    public int getDuracaoEmMinutos(){return duracaoEmMinutos;}
    public void setDuracaoEmMinutos(int duracaoEmMinutos){this.duracaoEmMinutos = duracaoEmMinutos;}
}