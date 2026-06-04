package com.barbearia.view.swing;

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

public class AgendamentoPanel extends JPanel {

    private final JTextField clientNameField = new JTextField(18);
    private final JTextField clientContactField = new JTextField(18);
    private final JTextField dateTimeField = new JTextField(18);
    private final JTextField barberIdField = new JTextField(18);
    private final JTextField serviceIdField = new JTextField(18);
    private final JTextField statusField = new JTextField(18);

    public AgendamentoPanel() {
        super(new BorderLayout(16, 16));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        add(criarFormulario(), BorderLayout.WEST);
        add(criarTabela(), BorderLayout.CENTER);
    }

    private JPanel criarFormulario() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Dados do agendamento"));

        adicionarCampo(form, 0, "Cliente", clientNameField);
        adicionarCampo(form, 1, "Contato", clientContactField);
        adicionarCampo(form, 2, "Data e hora", dateTimeField);
        adicionarCampo(form, 3, "ID do barbeiro", barberIdField);
        adicionarCampo(form, 4, "ID do serviço", serviceIdField);
        adicionarCampo(form, 5, "Status", statusField);
        adicionarBotoes(form, 6);

        return form;
    }

    private JScrollPane criarTabela() {
        String[] columns = {"ID", "Cliente", "Data/Hora", "Barbeiro", "Serviço", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        return new JScrollPane(table);
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
        buttons.add(criarBotaoPendente("Cadastrar"));
        buttons.add(criarBotaoPendente("Atualizar"));
        buttons.add(criarBotaoPendente("Cancelar"));

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
        clientNameField.setText("");
        clientContactField.setText("");
        dateTimeField.setText("");
        barberIdField.setText("");
        serviceIdField.setText("");
        statusField.setText("");
    }

    private void mostrarAcaoPendente() {
        JOptionPane.showMessageDialog(
            this,
            "A integração desta ação será feita na próxima etapa.",
            "Em desenvolvimento",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
