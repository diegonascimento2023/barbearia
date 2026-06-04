package com.barbearia.view.swing;

import com.barbearia.controller.BarbeiroController;
import com.barbearia.model.Barbeiro;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

public class BarbeiroPanel extends JPanel {

    private final BarbeiroController barbeiroController = new BarbeiroController();
    private final DefaultTableModel tableModel;
    private JTable table;
    private final JTextField nameField = new JTextField(18);
    private final JTextField loginField = new JTextField(18);
    private final JPasswordField passwordField = new JPasswordField(18);
    private final JTextField startTimeField = new JTextField(18);
    private final JTextField endTimeField = new JTextField(18);

    public BarbeiroPanel() {
        super(new BorderLayout(16, 16));
        tableModel = criarModeloTabela();
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        add(criarFormulario(), BorderLayout.WEST);
        add(criarTabela(), BorderLayout.CENTER);
        carregarBarbeiros();
    }

    private JPanel criarFormulario() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Dados do barbeiro"));

        adicionarCampo(form, 0, "Nome", nameField);
        adicionarCampo(form, 1, "Login", loginField);
        adicionarCampo(form, 2, "Senha", passwordField);
        adicionarCampo(form, 3, "Início (HH:mm)", startTimeField);
        adicionarCampo(form, 4, "Fim (HH:mm)", endTimeField);
        adicionarBotoes(form, 5);

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
        String[] columns = {"ID", "Nome", "Login", "Início", "Fim", "Ativo"};
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
        createButton.addActionListener(event -> cadastrarBarbeiro());
        buttons.add(createButton);

        JButton updateButton = new JButton("Atualizar");
        updateButton.addActionListener(event -> atualizarBarbeiro());
        buttons.add(updateButton);

        JButton deactivateButton = new JButton("Desativar");
        deactivateButton.addActionListener(event -> desativarBarbeiro());
        buttons.add(deactivateButton);

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
        nameField.setText("");
        loginField.setText("");
        passwordField.setText("");
        startTimeField.setText("");
        endTimeField.setText("");
        table.clearSelection();
    }

    private void carregarBarbeiros() {
        try {
            List<Barbeiro> barbeiros = barbeiroController.listarTodos();
            tableModel.setRowCount(0);

            for (Barbeiro barbeiro : barbeiros) {
                tableModel.addRow(new Object[] {
                    barbeiro.getId(),
                    barbeiro.getNome(),
                    barbeiro.getLogin(),
                    barbeiro.getHorarioInicio(),
                    barbeiro.getHorarioFim(),
                    barbeiro.isAtivo() ? "Sim" : "Não"
                });
            }
        } catch (SQLException error) {
            mostrarErro("Erro ao carregar barbeiros: " + error.getMessage());
        }
    }

    private void cadastrarBarbeiro() {
        try {
            Barbeiro barbeiro = lerBarbeiroDoFormulario();
            barbeiroController.criar(barbeiro);
            limparCampos();
            carregarBarbeiros();
            mostrarMensagem("Barbeiro cadastrado com sucesso.");
        } catch (RuntimeException | SQLException error) {
            mostrarErro(error.getMessage());
        }
    }

    private void atualizarBarbeiro() {
        try {
            Long id = lerIdSelecionado();
            Barbeiro barbeiro = lerBarbeiroDoFormulario();
            barbeiroController.atualizar(id, barbeiro);
            limparCampos();
            carregarBarbeiros();
            mostrarMensagem("Barbeiro atualizado com sucesso.");
        } catch (RuntimeException | SQLException error) {
            mostrarErro(error.getMessage());
        }
    }

    private void desativarBarbeiro() {
        try {
            Long id = lerIdSelecionado();
            barbeiroController.desativar(id);
            limparCampos();
            carregarBarbeiros();
            mostrarMensagem("Barbeiro desativado com sucesso.");
        } catch (RuntimeException | SQLException error) {
            mostrarErro(error.getMessage());
        }
    }

    private Barbeiro lerBarbeiroDoFormulario() {
        String nome = nameField.getText().trim();
        String login = loginField.getText().trim();
        String senha = new String(passwordField.getPassword()).trim();
        String inicioTexto = startTimeField.getText().trim();
        String fimTexto = endTimeField.getText().trim();

        if (nome.isBlank() || login.isBlank() || senha.isBlank() || inicioTexto.isBlank() || fimTexto.isBlank()) {
            throw new IllegalArgumentException("Preencha todos os campos.");
        }

        return new Barbeiro(
            0L,
            nome,
            login,
            senha,
            lerHorario(inicioTexto),
            lerHorario(fimTexto),
            true
        );
    }

    private LocalTime lerHorario(String horarioTexto) {
        if (horarioTexto.matches("\\d{1,2}")) {
            int hora = Integer.parseInt(horarioTexto);
            return LocalTime.of(hora, 0);
        }

        if (horarioTexto.matches("\\d{1,2}:\\d{2}")) {
            String[] partes = horarioTexto.split(":");
            int hora = Integer.parseInt(partes[0]);
            int minuto = Integer.parseInt(partes[1]);
            return LocalTime.of(hora, minuto);
        }

        return LocalTime.parse(horarioTexto);
    }

    private Long lerIdSelecionado() {
        int row = table.getSelectedRow();

        if (row < 0) {
            throw new IllegalArgumentException("Selecione um barbeiro na tabela.");
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
            Barbeiro barbeiro = barbeiroController.buscarPorId(id);
            nameField.setText(barbeiro.getNome());
            loginField.setText(barbeiro.getLogin());
            passwordField.setText(barbeiro.getSenha());
            startTimeField.setText(barbeiro.getHorarioInicio().toString());
            endTimeField.setText(barbeiro.getHorarioFim().toString());
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
