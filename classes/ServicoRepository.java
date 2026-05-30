import java.util.ArrayList;
import java.util.List; 

public class ServicoRepository {
    // atributos da classe:
    // lista de contendo as instâncias dos serviços criadas
    private List<Servico> fonteDados = new ArrayList<>();

    // método para CRIAR um serviço
    public void criar(Servico servico){
        fonteDados.add(servico);
    }

    // método para BUSCAR SERVIÇO POR ID
    public Servico buscarPorId(Long id){
        for(Servico s: fonteDados){
            if(s.getId().equals(id)){
                return s;
            }
        }
        return null; //caso n encontre em fonteDados
    }

    // método para ATUALIZAR OS ATRIBUTOS DO SERVIÇO
    public void atualizar(Long id, Servico servicoAtualizado) {
        Servico s = buscarPorId(id); // 1) Buscando serviço a ser atualizado pelo ID
        if (s != null) { // 2) Caso a instancia do serviço encontrada não seja nula...
            s.setNome(servicoAtualizado.getNome());
            s.setDescricao(servicoAtualizado.getDescricao());
            s.setPreco(servicoAtualizado.getPreco());
            s.setDuracaoEmMinutos(servicoAtualizado.getDuracaoEmMinutos());
        }
    }

    // método para REMOVER o serviço 
    public void remover(Long id) {
        Servico s = buscarPorId(id);
        if (s != null) {
            fonteDados.remove(s); //removendo da lista de serviços
        }
    }

    // método para listar todas as instâncias de serviços
    public List<Servico> listarTodos(){
        return new ArrayList<>(fonteDados);
    }
}