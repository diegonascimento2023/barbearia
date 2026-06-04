package com.barbearia.view.swing;

import com.barbearia.controller.ServicoController;
import com.barbearia.model.Servico;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.List;

public class ServicoPanel extends JPanel {

    private final ServicoController servicoController = new ServicoController();
    private final DefaultTableModel tableModel;
    private JTable table;
    private final JTextField nameField = new JTextField(18);
    private final JTextArea descriptionArea = new JTextArea(4, 18);
    private final JTextField priceField = new JTextField(18);
    private final JTextField durationField = new JTextField(18);

    public ServicoPanel() {
        super(new BorderLayout(16, 16));
        tableModel = criarModeloTabela();
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        add(criarFormulario(), BorderLayout.WEST);
        add(criarTabela(), BorderLayout.CENTER);
        carregarServicos();
    }

    private JPanel criarFormulario() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Dados do serviço"));

        adicionarCampo(form, 0, "Nome", nameField);
        adicionarArea(form, 1, "Descrição", descriptionArea);
        adicionarCampo(form, 2, "Preço", priceField);
        adicionarCampo(form, 3, "Duração (min)", durationField);
        adicionarBotoes(form, 4);

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
        String[] columns = {"ID", "Nome", "Preço", "Duração"};
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

    private void adicionarArea(JPanel form, int row, String label, JTextArea area) {
        GridBagConstraints labelConstraints = criarRestricoes(0, row);
        labelConstraints.anchor = GridBagConstraints.NORTHWEST;
        form.add(new JLabel(label), labelConstraints);

        GridBagConstraints areaConstraints = criarRestricoes(1, row);
        areaConstraints.fill = GridBagConstraints.BOTH;
        form.add(new JScrollPane(area), areaConstraints);
    }

    private void adicionarBotoes(JPanel form, int row) {
        JPanel buttons = new JPanel();
        JButton createButton = new JButton("Cadastrar");
        createButton.addActionListener(event -> cadastrarServico());
        buttons.add(createButton);

        JButton updateButton = new JButton("Atualizar");
        updateButton.addActionListener(event -> atualizarServico());
        buttons.add(updateButton);

        JButton removeButton = new JButton("Remover");
        removeButton.addActionListener(event -> removerServico());
        buttons.add(removeButton);

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
        descriptionArea.setText("");
        priceField.setText("");
        durationField.setText("");
        table.clearSelection();
    }

    private void carregarServicos() {
        try {
            List<Servico> servicos = servicoController.listarTodos();
            tableModel.setRowCount(0);

            for (Servico servico : servicos) {
                tableModel.addRow(new Object[] {
                    servico.getId(),
                    servico.getNome(),
                    servico.getPreco(),
                    servico.getDuracaoEmMinutos()
                });
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(
                this,
                "Erro ao carregar serviços: " + error.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void cadastrarServico() {
        try {
            Servico servico = lerServicoDoFormulario();
            servicoController.criar(servico);
            limparCampos();
            carregarServicos();
            mostrarMensagem("Serviço cadastrado com sucesso.");
        } catch (IllegalArgumentException | SQLException error) {
            mostrarErro(error.getMessage());
        }
    }

    private void atualizarServico() {
        try {
            Long id = lerIdSelecionado();
            Servico servico = lerServicoDoFormulario();
            servicoController.atualizar(id, servico);
            limparCampos();
            carregarServicos();
            mostrarMensagem("Serviço atualizado com sucesso.");
        } catch (IllegalArgumentException | SQLException error) {
            mostrarErro(error.getMessage());
        }
    }

    private void removerServico() {
        try {
            Long id = lerIdSelecionado();
            servicoController.remover(id);
            limparCampos();
            carregarServicos();
            mostrarMensagem("Serviço removido com sucesso.");
        } catch (IllegalArgumentException | SQLException error) {
            mostrarErro(error.getMessage());
        }
    }

    private Servico lerServicoDoFormulario() {
        String nome = nameField.getText().trim();
        String descricao = descriptionArea.getText().trim();
        String precoTexto = priceField.getText().trim().replace(",", ".");
        String duracaoTexto = durationField.getText().trim();

        if (nome.isBlank() || descricao.isBlank() || precoTexto.isBlank() || duracaoTexto.isBlank()) {
            throw new IllegalArgumentException("Preencha todos os campos.");
        }

        return new Servico(
            0L,
            nome,
            descricao,
            new java.math.BigDecimal(precoTexto),
            Integer.parseInt(duracaoTexto)
        );
    }

    private Long lerIdSelecionado() {
        int row = table.getSelectedRow();

        if (row < 0) {
            throw new IllegalArgumentException("Selecione um serviço na tabela.");
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
            Servico servico = servicoController.buscarPorId(id);
            nameField.setText(servico.getNome());
            descriptionArea.setText(servico.getDescricao());
            priceField.setText(servico.getPreco().toString());
            durationField.setText(String.valueOf(servico.getDuracaoEmMinutos()));
        } catch (IllegalArgumentException | SQLException error) {
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
