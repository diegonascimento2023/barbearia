package com.barbearia.view.swing;

import com.barbearia.controller.AgendamentoController;
import com.barbearia.model.Agendamento;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AgendamentoPanel extends JPanel {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final AgendamentoController agendamentoController = new AgendamentoController();
    private final DefaultTableModel tableModel;
    private JTable table;
    private final JTextField clientNameField = new JTextField(18);
    private final JTextField clientContactField = new JTextField(18);
    private final JTextField dateTimeField = new JTextField(18);
    private final JTextField barberIdField = new JTextField(18);
    private final JTextField serviceIdField = new JTextField(18);
    private final JTextField statusField = new JTextField(18);

    public AgendamentoPanel() {
        super(new BorderLayout(16, 16));
        tableModel = criarModeloTabela();
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        add(criarFormulario(), BorderLayout.WEST);
        add(criarTabela(), BorderLayout.CENTER);
        carregarAgendamentos();
    }

    private JPanel criarFormulario() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Dados do agendamento"));

        adicionarCampo(form, 0, "Cliente", clientNameField);
        adicionarCampo(form, 1, "Contato", clientContactField);
        adicionarCampo(form, 2, "Data e hora (dd/MM/yyyy HH:mm)", dateTimeField);
        adicionarCampo(form, 3, "ID do barbeiro", barberIdField);
        adicionarCampo(form, 4, "ID do serviço", serviceIdField);
        adicionarCampo(form, 5, "Status", statusField);
        adicionarBotoes(form, 6);

        return form;
    }

    private JScrollPane criarTabela() {
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                preencherFormularioSelecionado();
            }
        });
        return new JScrollPane(table);
    }

    private DefaultTableModel criarModeloTabela() {
        String[] columns = {"ID", "Cliente", "Data/Hora", "Barbeiro", "Serviço", "Status"};
        return new DefaultTableModel(columns, 0);
    }

    private void adicionarCampo(JPanel form, int row, String label, JTextField field) {
        GridBagConstraints labelConstraints = criarRestricoes(0, row);
        labelConstraints.anchor = GridBagConstraints.WEST;
        form.add(new JLabel(label), labelConstraints);

        GridBagConstraints fieldConstraints = criarRestricoes(1, row);
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        form.add(field, fieldConstraints);
    }

    private void adicionarBotoes(JPanel form, int row) {
        JPanel buttons = new JPanel();
        JButton createButton = new JButton("Cadastrar");
        createButton.addActionListener(event -> cadastrarAgendamento());
        buttons.add(createButton);

        JButton updateButton = new JButton("Atualizar");
        updateButton.addActionListener(event -> atualizarAgendamento());
        buttons.add(updateButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(event -> cancelarAgendamento());
        buttons.add(cancelButton);

        JButton clearButton = new JButton("Limpar");
        clearButton.addActionListener(event -> limparCampos());
        buttons.add(clearButton);

        GridBagConstraints constraints = criarRestricoes(0, row);
        constraints.gridwidth = 2;
        form.add(buttons, constraints);
    }

    private GridBagConstraints criarRestricoes(int column, int row) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = column;
        constraints.gridy = row;
        constraints.insets = new Insets(6, 6, 6, 6);
        return constraints;
    }

    private void limparCampos() {
        clientNameField.setText("");
        clientContactField.setText("");
        dateTimeField.setText("");
        barberIdField.setText("");
        serviceIdField.setText("");
        statusField.setText("");
        table.clearSelection();
    }

    private void carregarAgendamentos() {
        try {
            List<Agendamento> agendamentos = agendamentoController.listarTodos();
            tableModel.setRowCount(0);

            for (Agendamento agendamento : agendamentos) {
                tableModel.addRow(new Object[] {
                    agendamento.getId(),
                    agendamento.getNomeCliente(),
                    agendamento.getDataHora().format(DATE_TIME_FORMATTER),
                    agendamento.getIdBarbeiro(),
                    agendamento.getIdServico(),
                    agendamento.getStatus()
                });
            }
        } catch (SQLException error) {
            mostrarErro("Erro ao carregar agendamentos: " + error.getMessage());
        }
    }

    private void cadastrarAgendamento() {
        try {
            Agendamento agendamento = lerAgendamentoDoFormulario();
            agendamentoController.criar(agendamento);
            limparCampos();
            carregarAgendamentos();
            mostrarMensagem("Agendamento cadastrado com sucesso.");
        } catch (RuntimeException | SQLException error) {
            mostrarErro(error.getMessage());
        }
    }

    private void atualizarAgendamento() {
        try {
            Long id = lerIdSelecionado();
            Agendamento agendamento = lerAgendamentoDoFormulario();
            agendamentoController.atualizar(id, agendamento);
            limparCampos();
            carregarAgendamentos();
            mostrarMensagem("Agendamento atualizado com sucesso.");
        } catch (RuntimeException | SQLException error) {
            mostrarErro(error.getMessage());
        }
    }

    private void cancelarAgendamento() {
        try {
            Long id = lerIdSelecionado();
            agendamentoController.cancelar(id);
            limparCampos();
            carregarAgendamentos();
            mostrarMensagem("Agendamento cancelado com sucesso.");
        } catch (RuntimeException | SQLException error) {
            mostrarErro(error.getMessage());
        }
    }

    private Agendamento lerAgendamentoDoFormulario() {
        String nomeCliente = clientNameField.getText().trim();
        String contatoCliente = clientContactField.getText().trim();
        String dataHoraTexto = dateTimeField.getText().trim();
        String idBarbeiroTexto = barberIdField.getText().trim();
        String idServicoTexto = serviceIdField.getText().trim();
        String status = statusField.getText().trim();

        if (nomeCliente.isBlank() || contatoCliente.isBlank() || dataHoraTexto.isBlank() ||
            idBarbeiroTexto.isBlank() || idServicoTexto.isBlank()) {
            throw new IllegalArgumentException("Preencha todos os campos obrigatórios.");
        }

        if (status.isBlank()) {
            status = "pendente";
        }

        return new Agendamento(
            0L,
            nomeCliente,
            contatoCliente,
            LocalDateTime.parse(dataHoraTexto, DATE_TIME_FORMATTER),
            status,
            Long.valueOf(idBarbeiroTexto),
            Long.valueOf(idServicoTexto)
        );
    }

    private Long lerIdSelecionado() {
        int row = table.getSelectedRow();

        if (row < 0) {
            throw new IllegalArgumentException("Selecione um agendamento na tabela.");
        }

        return Long.valueOf(table.getValueAt(row, 0).toString());
    }

    private void preencherFormularioSelecionado() {
        int row = table.getSelectedRow();

        if (row < 0) {
            return;
        }

        try {
            Long id = Long.valueOf(table.getValueAt(row, 0).toString());
            Agendamento agendamento = agendamentoController.buscarPorId(id);
            clientNameField.setText(agendamento.getNomeCliente());
            clientContactField.setText(agendamento.getContatoCliente());
            dateTimeField.setText(agendamento.getDataHora().format(DATE_TIME_FORMATTER));
            barberIdField.setText(agendamento.getIdBarbeiro().toString());
            serviceIdField.setText(agendamento.getIdServico().toString());
            statusField.setText(agendamento.getStatus());
        } catch (RuntimeException | SQLException error) {
            mostrarErro(error.getMessage());
        }
    }

    private void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
