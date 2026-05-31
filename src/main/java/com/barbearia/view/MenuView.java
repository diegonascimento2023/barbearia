package com.barbearia.view;

import com.barbearia.controller.AgendamentoController;
import com.barbearia.controller.BarbeiroController;
import com.barbearia.controller.ServicoController;
import com.barbearia.model.Agendamento;
import com.barbearia.model.Barbeiro;
import com.barbearia.model.Servico;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuView {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final Scanner scanner;
    private final BarbeiroController barbeiroController;
    private final ServicoController servicoController;
    private final AgendamentoController agendamentoController;

    public MenuView() {
        this(
            new Scanner(System.in),
            new BarbeiroController(),
            new ServicoController(),
            new AgendamentoController()
        );
    }

    public MenuView(
        Scanner scanner,
        BarbeiroController barbeiroController,
        ServicoController servicoController,
        AgendamentoController agendamentoController
    ) {
        this.scanner = scanner;
        this.barbeiroController = barbeiroController;
        this.servicoController = servicoController;
        this.agendamentoController = agendamentoController;
    }

    public static void main(String[] args) {
        new MenuView().iniciar();
    }

    public void iniciar() {
        int opcao;

        do {
            mostrarMenuPrincipal();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> abrirMenuBarbeiros();
                case 2 -> abrirMenuServicos();
                case 3 -> abrirMenuAgendamentos();
                case 0 -> System.out.println("Sistema encerrado.");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void mostrarMenuPrincipal() {
        System.out.println();
        System.out.println("===== Sistema da Barbearia =====");
        System.out.println("1 - Barbeiros");
        System.out.println("2 - Serviços");
        System.out.println("3 - Agendamentos");
        System.out.println("0 - Sair");
    }

    private void abrirMenuBarbeiros() {
        int opcao;

        do {
            System.out.println();
            System.out.println("----- Barbeiros -----");
            System.out.println("1 - Cadastrar barbeiro");
            System.out.println("2 - Listar barbeiros");
            System.out.println("3 - Atualizar barbeiro");
            System.out.println("4 - Desativar barbeiro");
            System.out.println("0 - Voltar");
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> executarAcao(this::cadastrarBarbeiro);
                case 2 -> executarAcao(this::listarBarbeiros);
                case 3 -> executarAcao(this::atualizarBarbeiro);
                case 4 -> executarAcao(this::desativarBarbeiro);
                case 0 -> { }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrarBarbeiro() throws SQLException {
        Barbeiro barbeiro = lerDadosBarbeiro();
        barbeiroController.criar(barbeiro);
        System.out.println("Barbeiro cadastrado com sucesso.");
    }

    private void listarBarbeiros() throws SQLException {
        List<Barbeiro> barbeiros = barbeiroController.listarTodos();

        if (barbeiros.isEmpty()) {
            System.out.println("Nenhum barbeiro cadastrado.");
            return;
        }

        for (Barbeiro barbeiro : barbeiros) {
            System.out.printf(
                "#%d - %s | login: %s | %s às %s%n",
                barbeiro.getId(),
                barbeiro.getNome(),
                barbeiro.getLogin(),
                formatarHora(barbeiro.getHorarioInicio()),
                formatarHora(barbeiro.getHorarioFim())
            );
        }
    }

    private void atualizarBarbeiro() throws SQLException {
        Long id = lerLong("ID do barbeiro: ");
        Barbeiro barbeiro = lerDadosBarbeiro();
        barbeiroController.atualizar(id, barbeiro);
        System.out.println("Barbeiro atualizado com sucesso.");
    }

    private void desativarBarbeiro() throws SQLException {
        Long id = lerLong("ID do barbeiro: ");
        barbeiroController.desativar(id);
        System.out.println("Barbeiro desativado com sucesso.");
    }

    private Barbeiro lerDadosBarbeiro() {
        String nome = lerTextoObrigatorio("Nome: ");
        String login = lerTextoObrigatorio("Login: ");
        String senha = lerTextoObrigatorio("Senha: ");
        LocalTime horarioInicio = lerHorario("Horário de início (HH:mm): ");
        LocalTime horarioFim = lerHorario("Horário de fim (HH:mm): ");

        return new Barbeiro(0L, nome, login, senha, horarioInicio, horarioFim, true);
    }

    private void abrirMenuServicos() {
        int opcao;

        do {
            System.out.println();
            System.out.println("----- Serviços -----");
            System.out.println("1 - Cadastrar serviço");
            System.out.println("2 - Listar serviços");
            System.out.println("3 - Atualizar serviço");
            System.out.println("4 - Remover serviço");
            System.out.println("0 - Voltar");
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> executarAcao(this::cadastrarServico);
                case 2 -> executarAcao(this::listarServicos);
                case 3 -> executarAcao(this::atualizarServico);
                case 4 -> executarAcao(this::removerServico);
                case 0 -> { }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrarServico() throws SQLException {
        Servico servico = lerDadosServico();
        servicoController.criar(servico);
        System.out.println("Serviço cadastrado com sucesso.");
    }

    private void listarServicos() throws SQLException {
        List<Servico> servicos = servicoController.listarTodos();

        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço cadastrado.");
            return;
        }

        for (Servico servico : servicos) {
            System.out.printf(
                "#%d - %s | R$ %s | %d min%n",
                servico.getId(),
                servico.getNome(),
                servico.getPreco(),
                servico.getDuracaoEmMinutos()
            );
        }
    }

    private void atualizarServico() throws SQLException {
        Long id = lerLong("ID do serviço: ");
        Servico servico = lerDadosServico();
        servicoController.atualizar(id, servico);
        System.out.println("Serviço atualizado com sucesso.");
    }

    private void removerServico() throws SQLException {
        Long id = lerLong("ID do serviço: ");
        servicoController.remover(id);
        System.out.println("Serviço removido com sucesso.");
    }

    private Servico lerDadosServico() {
        String nome = lerTextoObrigatorio("Nome: ");
        String descricao = lerTextoObrigatorio("Descrição: ");
        BigDecimal preco = lerDecimal("Preço: ");
        int duracaoEmMinutos = lerInteiro("Duração em minutos: ");

        return new Servico(0L, nome, descricao, preco, duracaoEmMinutos);
    }

    private void abrirMenuAgendamentos() {
        int opcao;

        do {
            System.out.println();
            System.out.println("----- Agendamentos -----");
            System.out.println("1 - Cadastrar agendamento");
            System.out.println("2 - Listar agendamentos");
            System.out.println("3 - Listar por data");
            System.out.println("4 - Listar por barbeiro");
            System.out.println("5 - Atualizar agendamento");
            System.out.println("6 - Cancelar agendamento");
            System.out.println("0 - Voltar");
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> executarAcao(this::cadastrarAgendamento);
                case 2 -> executarAcao(this::listarAgendamentos);
                case 3 -> executarAcao(this::listarAgendamentosPorData);
                case 4 -> executarAcao(this::listarAgendamentosPorBarbeiro);
                case 5 -> executarAcao(this::atualizarAgendamento);
                case 6 -> executarAcao(this::cancelarAgendamento);
                case 0 -> { }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrarAgendamento() throws SQLException {
        Agendamento agendamento = lerDadosAgendamento("pendente");
        agendamentoController.criar(agendamento);
        System.out.println("Agendamento cadastrado com sucesso.");
    }

    private void listarAgendamentos() throws SQLException {
        imprimirAgendamentos(agendamentoController.listarTodos());
    }

    private void listarAgendamentosPorData() throws SQLException {
        LocalDate data = lerData("Data (dd/MM/yyyy): ");
        imprimirAgendamentos(agendamentoController.listarPorData(data));
    }

    private void listarAgendamentosPorBarbeiro() throws SQLException {
        Long idBarbeiro = lerLong("ID do barbeiro: ");
        imprimirAgendamentos(agendamentoController.listarPorBarbeiro(idBarbeiro));
    }

    private void atualizarAgendamento() throws SQLException {
        Long id = lerLong("ID do agendamento: ");
        String status = lerTextoObrigatorio("Status: ");
        Agendamento agendamento = lerDadosAgendamento(status);
        agendamentoController.atualizar(id, agendamento);
        System.out.println("Agendamento atualizado com sucesso.");
    }

    private void cancelarAgendamento() throws SQLException {
        Long id = lerLong("ID do agendamento: ");
        agendamentoController.cancelar(id);
        System.out.println("Agendamento cancelado com sucesso.");
    }

    private Agendamento lerDadosAgendamento(String status) {
        String nomeCliente = lerTextoObrigatorio("Nome do cliente: ");
        String contatoCliente = lerTextoObrigatorio("Contato do cliente: ");
        LocalDateTime dataHora = lerDataHora("Data e hora (dd/MM/yyyy HH:mm): ");
        Long idBarbeiro = lerLong("ID do barbeiro: ");
        Long idServico = lerLong("ID do serviço: ");

        return new Agendamento(0L, nomeCliente, contatoCliente, dataHora, status, idBarbeiro, idServico);
    }

    private void imprimirAgendamentos(List<Agendamento> agendamentos) {
        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento encontrado.");
            return;
        }

        for (Agendamento agendamento : agendamentos) {
            System.out.printf(
                "#%d - %s | %s | barbeiro #%d | serviço #%d | status: %s%n",
                agendamento.getId(),
                agendamento.getNomeCliente(),
                formatarDataHora(agendamento.getDataHora()),
                agendamento.getIdBarbeiro(),
                agendamento.getIdServico(),
                agendamento.getStatus()
            );
        }
    }

    private void executarAcao(AcaoMenu acao) {
        try {
            acao.executar();
        } catch (IllegalArgumentException | IllegalStateException erro) {
            System.out.println("Atenção: " + erro.getMessage());
        } catch (SQLException erro) {
            System.out.println("Erro ao acessar o banco de dados: " + erro.getMessage());
        }
    }

    private String lerTextoObrigatorio(String mensagem) {
        String valor;

        do {
            System.out.print(mensagem);
            valor = scanner.nextLine().trim();

            if (valor.isBlank()) {
                System.out.println("Campo obrigatório.");
            }
        } while (valor.isBlank());

        return valor;
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException erro) {
                System.out.println("Informe um número inteiro válido.");
            }
        }
    }

    private Long lerLong(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException erro) {
                System.out.println("Informe um ID válido.");
            }
        }
    }

    private BigDecimal lerDecimal(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                String valor = scanner.nextLine().trim().replace(",", ".");
                return new BigDecimal(valor);
            } catch (NumberFormatException erro) {
                System.out.println("Informe um valor decimal válido.");
            }
        }
    }

    private LocalDate lerData(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return LocalDate.parse(scanner.nextLine().trim(), DATE_FORMATTER);
            } catch (DateTimeParseException erro) {
                System.out.println("Informe uma data no formato dd/MM/yyyy.");
            }
        }
    }

    private LocalDateTime lerDataHora(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return LocalDateTime.parse(scanner.nextLine().trim(), DATE_TIME_FORMATTER);
            } catch (DateTimeParseException erro) {
                System.out.println("Informe data e hora no formato dd/MM/yyyy HH:mm.");
            }
        }
    }

    private LocalTime lerHorario(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return LocalTime.parse(scanner.nextLine().trim(), TIME_FORMATTER);
            } catch (DateTimeParseException erro) {
                System.out.println("Informe um horário no formato HH:mm.");
            }
        }
    }

    private String formatarHora(LocalTime horario) {
        if (horario == null) {
            return "-";
        }

        return horario.format(TIME_FORMATTER);
    }

    private String formatarDataHora(LocalDateTime dataHora) {
        if (dataHora == null) {
            return "-";
        }

        return dataHora.format(DATE_TIME_FORMATTER);
    }

    @FunctionalInterface
    private interface AcaoMenu {
        void executar() throws SQLException;
    }
}
