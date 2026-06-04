package com.barbearia.view.swing;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Font;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Sistema da Barbearia");
        configurarJanela();
        setContentPane(criarConteudo());
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private JComponent criarConteudo() {
        JPanel root = new JPanel(new BorderLayout());
        root.add(criarCabecalho(), BorderLayout.NORTH);
        root.add(criarAbas(), BorderLayout.CENTER);
        return root;
    }

    private JComponent criarCabecalho() {
        JLabel title = new JLabel("Sistema da Barbearia");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        title.setBorder(BorderFactory.createEmptyBorder(16, 18, 12, 18));
        return title;
    }

    private JComponent criarAbas() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Barbeiros", new BarbeiroPanel());
        tabs.addTab("Serviços", new ServicoPanel());
        return tabs;
    }
}
