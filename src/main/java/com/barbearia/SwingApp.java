package com.barbearia;

import com.barbearia.database.DatabaseConnection;
import com.barbearia.view.swing.MainFrame;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SwingApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SwingApp::start);
    }

    private static void start() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            DatabaseConnection.inicializarBancoDeDados();
            new MainFrame().setVisible(true);
        } catch (Exception error) {
            JOptionPane.showMessageDialog(
                null,
                "Erro ao iniciar a interface: " + error.getMessage(),
                "Sistema da Barbearia",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
