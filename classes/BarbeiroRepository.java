import java.util.ArrayList;
import java.util.List;

public class BarbeiroRepository {
    // atributos da classe:
    // lista de contendo as instâncias dos barbeiros criadas
    private List<Barbeiro> fonteDados = new ArrayList<>();

    // método para CRIAR um barbeiro
    public void criar(Barbeiro barbeiro){
        fonteDados.add(barbeiro);
    }

    // método para BUSCAR BARBEIRO POR ID
    public Barbeiro buscarPorId(Long id){
        for(Barbeiro b : fonteDados){
            if(b.getId().equals(id)){
                return b; 
            }
        }
        return null; //caso n encontre em fonteDados
    }

    // método para ATUALIZAR OS ATRIBUTOS DO BARBEIRO 
    public void atualizar(Long id, Barbeiro barbeiroAtualizado){
        Barbeiro b = buscarPorId(id); // 1) Buscando barbeiro a ser atualizado pelo ID

        if (b != null){ // 2) Caso a instancia do barbeiro encontrada não seja nula...
            b.setNome(barbeiroAtualizado.getNome());
            b.setLogin(barbeiroAtualizado.getLogin());
            b.setSenha(barbeiroAtualizado.getSenha());
            b.setHorarioInicio(barbeiroAtualizado.getHorarioInicio());
            b.setHorarioFim(barbeiroAtualizado.getHorarioFim());
            b.setAtivo(barbeiroAtualizado.getAtivo());
        }   
    }

    // método para DESATIVAR o barbeiro (deixar ele indisponível)
    public void desativar(Long id){
        Barbeiro b = buscarPorId(id); // 1) Buscando o barbeiro a ser desativado pelo ID
        if (b != null){
            b.setAtivo(false);
        }
    }

    // método para listar todas as instâncias de barbeiros 
    public List<Barbeiro> listarTodos(){
        return new ArrayList<>(fonteDados);
    }
}