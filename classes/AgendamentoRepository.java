import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoRepository {
    // atributos da classe:
    // lista de contendo as instâncias dos agendamentos criadas
    private List<Agendamento> fonteDados = new ArrayList<>();

    // método para CRIAR um agendamento
    public void criar(Agendamento agendamento){
        fonteDados.add(agendamento);
    }

    // método para BUSCAR AGENDAMENTO POR ID
    public Agendamento buscarPorId(Long id){
        for(Agendamento a : fonteDados){
            if(a.getId().equals(id)){
                return a;
            }
        }
        return null; //caso n encontre em fonteDados
    }

    // método para ATUALIZAR OS ATRIBUTOS DO AGENDAMENTO
    public void atualizar(Long id, Agendamento agendamentoAtualizado){
        Agendamento a = buscarPorId(id); // 1) Buscando agendamento a ser atualizado pelo ID
        if (a != null){ // 2) Caso a instancia do agendamento encontrada não seja nula...
            a.setNomeCliente(agendamentoAtualizado.getNomeCliente());
            a.setContatoCliente(agendamentoAtualizado.getContatoCliente());
            a.setDataHora(agendamentoAtualizado.getDataHora());
            a.setStatus(agendamentoAtualizado.getStatus());
            a.setIdBarbeiro(agendamentoAtualizado.getIdBarbeiro());
            a.setIdServico(agendamentoAtualizado.getIdServico());
        }
    }
    
    // método para CANCELAR O AGENDAMENTO (Status = "Cancelado")
    public void cancelar(Long id){
        Agendamento a = buscarPorId(id); // 1) Buscando agendamento a ser atualizado pelo ID
        if (a != null){
            a.setStatus("Cancelado");
        }
    }

    // método para LISTAR TODOS OS AGENDAMENTOS
    public List<Agendamento> listarTodos(){
        return new ArrayList<>(fonteDados);
    }
    // método para LISTAR TODOS OS AGENDAMENTOS (POR DATA)
    public List<Agendamento> listarPorData(LocalDate data){
        List<Agendamento> filtrados = new ArrayList<>(); //criando lista para os agendamentos filtrados em ordem
        for(Agendamento a : fonteDados){
            if(a.getDataHora().toLocalDate().equals(data)){
                filtrados.add(a); //adicionando o agendamento para a lista filtrada por data
            }
        }
        return filtrados; // retorna a lista dos agendamentos filtrados em ordem
    }

    // método para LISTAR TODOS OS AGENDAMENTOS (POR BARBEIRO)
    public List<Agendamento> listarPorBarbeiro(Long idBarbeiro){
        List<Agendamento> filtrados = new ArrayList<>();
        for(Agendamento a : fonteDados){
            if(a.getIdBarbeiro().equals(idBarbeiro)){
                filtrados.add(a);
            }
        }
        return filtrados; 
    }

    // método para validação da disponibilidade do barbeiro
    public boolean validarDisponibilidade(LocalDateTime dataHora, Long idBarbeiro){
        //for-each de cada agendamento na lista de fonte de dados das instâncias
        for (Agendamento a : fonteDados){

            // Conflito de horário: barbeiro de ID "X" já estiver com uma mesma dataHora marcada e não estiver com status de "cancelado" nessa dataHora
            if (a.getIdBarbeiro().equals(idBarbeiro) && a.getDataHora().equals(dataHora) && !a.getStatus().equals("Cancelado")){
                return false;
            }
        }
        return true; // caso não caia no Conflito de horário
    }
}