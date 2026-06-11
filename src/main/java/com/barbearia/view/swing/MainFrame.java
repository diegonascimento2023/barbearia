package com.barbearia.view.swing;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
        
        BarbeiroPanel barbeiroPanel = new BarbeiroPanel();
        ServicoPanel servicoPanel = new ServicoPanel();
        AgendamentoPanel agendamentoPanel = new AgendamentoPanel();
        
        tabs.addTab("Barbeiros", barbeiroPanel);
        tabs.addTab("Serviços", servicoPanel);
        tabs.addTab("Agendamentos", agendamentoPanel);
        
        tabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabs.getSelectedIndex();
                if (selectedIndex == 0) {
                    barbeiroPanel.carregarBarbeiros();
                } else if (selectedIndex == 1) {
                    servicoPanel.carregarServicos();
                } else if (selectedIndex == 2) {
                    agendamentoPanel.carregarOpcoes();
                    agendamentoPanel.carregarAgendamentos();
                }
            }
        });
        
        return tabs;
    }
}
