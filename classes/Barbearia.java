import java.util.ArrayList; 
import java.util.List; 

public class Barbearia {
    //atributos da classe: barbearia
    private String nomeEstabelecimento;
    //delegações de classes
    private List<Barbeiro> barbeiros; //lista de barbeiros do estabelecimento
    private List<Servico> servicos; //lista de servicos do estabelecimento
    private List<Agendamento> agendamentos; //lista de agendamentos do estabelecimento

    //construtor da classe: barbearia
    public Barbearia(String nomeEstabelecimento){
        this.nomeEstabelecimento = nomeEstabelecimento; 
        this.barbeiros = new ArrayList<>();
        this.servicos = new ArrayList<>();
        this.agendamentos = new ArrayList();
    }

    //MÉTODOS 

    // nomeEstabelecimento: getter e setter
    public String getNomeEstabelecimento(){return nomeEstabelecimento;}
    public void setNomeEstabelecimento(String nomeEstabelecimento){this.nomeEstabelecimento = nomeEstabelecimento;}

    // método para ABRIR O ESTABELECIMENTO 
    public void abrirEstabelecimento(){
        System.out.println("A barbearia "+nomeEstabelecimento+" está aberta para atendimentos.");
    }
}