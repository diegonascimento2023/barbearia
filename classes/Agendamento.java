import java.time.LocalDateTime;

public class Agendamento {
    //atributos da classe: agendamento 
    private Long id;
    private String nomeCliente; 
    private String contatoCliente;
    private LocalDateTime dataHora; 
    private String status; //"agendado", "cancelado", etc
    private Long idBarbeiro; 
    private Long idServico;

    //construtor da classe: agendamento
    public Agendamento(Long id, String nomeCliente, String contatoCliente, LocalDateTime dataHora, String status, Long IdBarbeiro, Long IdServico){
        this.id = id;
        this.nomeCliente = nomeCliente; 
        this.contatoCliente = contatoCliente;
        this.dataHora = dataHora; 
        this.status = status; 
        this.idBarbeiro = IdBarbeiro; 
        this.idServico = IdServico; 
    }

    //MÉTODOS: GETTERS E SETTERS
    //id
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}
    //nome
    public String getNomeCliente(){return nomeCliente;}
    public void setNomeCliente(String nomeCliente){this.nomeCliente = nomeCliente;}
    //contatoCliente
    public String getContatoCliente(){return contatoCliente;}
    public void setContatoCliente(String contatoCliente){this.contatoCliente = contatoCliente;}
    //dataHora
    public LocalDateTime getDataHora(){return dataHora;}
    public void setDataHora(LocalDateTime dataHora){this.dataHora = dataHora;}
    //status
    public String getStatus(){return status;}
    public void setStatus(String status){this.status = status;}
    //IdBarbeiro
    public Long getIdBarbeiro(){return idBarbeiro;}
    public void setIdBarbeiro(Long idBarbeiro){this.idBarbeiro = idBarbeiro;}
    //IdServico
    public Long getIdServico(){return idServico;}
    public void setIdServico(Long idServico){this.idServico = idServico;}
}