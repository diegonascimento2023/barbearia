import java.time.LocalTime;

public class Barbeiro {
    //atributos da classe: barbeiro
    private Long id; 
    private String nome;
    private String login; 
    private String senha; 
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private boolean ativo;  

    //construtor da classe: barbeiro
    public Barbeiro(long id, String nome, String login, String senha, LocalTime horarioInicio, LocalTime horarioFim, boolean ativo){
        this.id = id; 
        this.nome = nome; 
        this.senha = senha; 
        this.horarioInicio = horarioInicio; 
        this.horarioFim = horarioFim; 
        this.ativo = true; //ao cadastrar um novo barbeiro, ele começa com status ativo
    }

    //MÉTODOS: GETTERS E SETTERS 
    //id
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}
    //nome
    public String getNome(){return nome;}
    public void setNome(String nome){this.nome = nome;}
    //login
    public String getLogin(){return login;}
    public void setLogin(String login){this.login = login;}
    //senha
    public String getSenha(){return senha;}
    public void setSenha(String senha){this.senha = senha;}
    //horarioInicio
    public LocalTime getHorarioInicio(){return horarioInicio;}
    public void setHorarioInicio(LocalTime horarioInicio){this.horarioInicio = horarioInicio;}
    //horarioFim
    public LocalTime getHorarioFim(){return horarioFim;}
    public void setHorarioFim(LocalTime horarioFim){this.horarioFim = horarioFim;}
    //ativo
    public boolean getAtivo(){return ativo;}
    public void setAtivo(boolean ativo){this.ativo = ativo;}
}
