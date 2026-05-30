import java.math.BigDecimal;

public class Servico {
    //atributos da classe: servico
    private Long id; 
    private String nome; 
    private String descricao; 
    private BigDecimal preco; 
    private int duracaoEmMinutos;
    
    //construtor da classe: servico
    public Servico(Long id, String nome, String descricao, BigDecimal preco, int duracaoEmMinutos){
        this.id = id;
        this.nome = nome; 
        this.descricao = descricao; 
        this.preco = preco; 
        this.duracaoEmMinutos = duracaoEmMinutos; 
    }

    //MÉTODOS: GETTERS E SETTERS
    //id
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}
    //nome
    public String getNome(){return nome;}
    public void setNome(String nome){this.nome = nome;}
    //descricao
    public String getDescricao(){return descricao;}
    public void setDescricao(String descricao){this.descricao = descricao;}
    //preco
    public BigDecimal getPreco(){return preco;}
    public void setPreco(BigDecimal preco){this.preco = preco;}
    //duracaoEmMinutos  
    public int getDuracaoEmMinutos(){return duracaoEmMinutos;}
    public void setDuracaoEmMinutos(int duracaoEmMinutos){this.duracaoEmMinutos = duracaoEmMinutos;}
}