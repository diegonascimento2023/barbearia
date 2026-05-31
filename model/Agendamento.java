package com.barbearia.model;

import java.time.LocalDateTime;

public class Agendamento {

    private Long id;
    private String nomeCliente; 
    private String contatoCliente;
    private LocalDateTime dataHora; 
    private String status; 
    private Long idBarbeiro; 
    private Long idServico;

    public Agendamento(Long id, String nomeCliente, String contatoCliente, LocalDateTime dataHora, String status, Long IdBarbeiro, Long IdServico){
        this.id = id;
        this.nomeCliente = nomeCliente; 
        this.contatoCliente = contatoCliente;
        this.dataHora = dataHora; 
        this.status = status; 
        this.idBarbeiro = IdBarbeiro; 
        this.idServico = IdServico; 
    }

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getNomeCliente(){return nomeCliente;}
    public void setNomeCliente(String nomeCliente){this.nomeCliente = nomeCliente;}

    public String getContatoCliente(){return contatoCliente;}
    public void setContatoCliente(String contatoCliente){this.contatoCliente = contatoCliente;}

    public LocalDateTime getDataHora(){return dataHora;}
    public void setDataHora(LocalDateTime dataHora){this.dataHora = dataHora;}

    public String getStatus(){return status;}
    public void setStatus(String status){this.status = status;}

    public Long getIdBarbeiro(){return idBarbeiro;}
    public void setIdBarbeiro(Long idBarbeiro){this.idBarbeiro = idBarbeiro;}

    public Long getIdServico(){return idServico;}
    public void setIdServico(Long idServico){this.idServico = idServico;}
}