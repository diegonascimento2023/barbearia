import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Demo{
    public static void main(String[] args) {
        System.out.println("--- INICIANDO TESTES DAS CLASSES ---\n");

        // instanciando etestando a barbearia (abrindo ela)
        Barbearia barbearia = new Barbearia("Barbearia UFERSA");
        barbearia.abrirEstabelecimento();
        System.out.println("Nome do estabelecimento: " + barbearia.getNomeEstabelecimento() + "\n");

        //instanciando e testandoum barbeiro + barbeiroRepository
        Barbeiro barbeiro1 = new Barbeiro(
                1L, 
                "Carlos (Corte Fino)", 
                "carlos.corte", 
                "123456", 
                LocalTime.of(8, 0), 
                LocalTime.of(18, 0), 
                true
        );

        //instanciando o repositório + criando um barbeiro
        BarbeiroRepository barbeiroRepo = new BarbeiroRepository();
        barbeiroRepo.criar(barbeiro1);
        
        //buscando o barbeiro plo id
        Barbeiro barbeiroBuscado = barbeiroRepo.buscarPorId(1L);
        System.out.println("Barbeiro encontrado no repositório: " + barbeiroBuscado.getNome());
        System.out.println("Horário de trabalho: " + barbeiroBuscado.getHorarioInicio() + " às " + barbeiroBuscado.getHorarioFim() + "\n");

        // instanciando e testando um serviço
        Servico corteCabelo = new Servico(
                100L, 
                "Corte Degradê", 
                "Corte moderno com degradê navalhado na régua, passando na 0", 
                new BigDecimal("45.00"), 
                30 // duração de 30 minutos uma sessão
        );
        System.out.println("Serviço criado: " + corteCabelo.getNome() + " - R$ " + corteCabelo.getPreco());
        System.out.println("Duração: " + corteCabelo.getDuracaoEmMinutos() + " minutos\n");

        // instanciando e testando um agendamento
        LocalDateTime dataHoraAgendamento = LocalDateTime.of(2026, 11, 20, 15, 30); //data de agendamento: 20/11/2023, às 15:30
        Agendamento agendamento = new Agendamento(
                10L, 
                "João Silva", 
                "(84) 98888-7777", 
                dataHoraAgendamento, 
                "Confirmado", 
                barbeiro1.getId(), 
                corteCabelo.getId()
        );

        System.out.println("--- DADOS DO AGENDAMENTO ---");
        System.out.println("Cliente que agendou: " + agendamento.getNomeCliente());
        System.out.println("Contato do Cliente: " + agendamento.getContatoCliente());
        System.out.println("Data e Hora do agendamento: " + agendamento.getDataHora());
        System.out.println("Status do agendamento: " + agendamento.getStatus());
        System.out.println("ID Barbeiro agendado: " + agendamento.getId()); // testando se IDs batem
        System.out.println("ID Serviço agendado: " + agendamento.getId());
        
        System.out.println("\n--- FIM DOS TESTES DAS CLASSES ---");
    }
}