package com.barbearia.view.swing;

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

public class BarbeiroPanel extends JPanel {

    private final JTextField nameField = new JTextField(18);
    private final JTextField loginField = new JTextField(18);
    private final JPasswordField passwordField = new JPasswordField(18);
    private final JTextField startTimeField = new JTextField(18);
    private final JTextField endTimeField = new JTextField(18);

    public BarbeiroPanel() {
        super(new BorderLayout(16, 16));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        add(criarFormulario(), BorderLayout.WEST);
        add(criarTabela(), BorderLayout.CENTER);
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
        String[] columns = {"ID", "Nome", "Login", "Início", "Fim", "Ativo"};
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
        buttons.add(criarBotaoPendente("Desativar"));

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
        loginField.setText("");
        passwordField.setText("");
        startTimeField.setText("");
        endTimeField.setText("");
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
