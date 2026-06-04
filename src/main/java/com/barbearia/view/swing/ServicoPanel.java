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
        JTable table = new JTable(tableModel);
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
        buttons.add(criarBotaoPendente("Cadastrar"));
        buttons.add(criarBotaoPendente("Atualizar"));
        buttons.add(criarBotaoPendente("Remover"));

        JButton clearButton = new JButton("Limpar");
        clearButton.addActionListener(event -> limparCampos());
        buttons.add(clearButton);

        GridBagConstraints constraints = criarRestricoes(0, row);
        constraints.gridwidth = 2;
        form.add(buttons, constraints);
    }

    private JButton criarBotaoPendente(String text) {
        JButton button = new JButton(text);
        button.addActionListener(event -> mostrarAcaoPendente());
        return button;
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
    }

    private void mostrarAcaoPendente() {
        JOptionPane.showMessageDialog(
            this,
            "A integração desta ação será feita na próxima etapa.",
            "Em desenvolvimento",
            JOptionPane.INFORMATION_MESSAGE
        );
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
}
