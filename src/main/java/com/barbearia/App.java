package com.barbearia;

import com.barbearia.database.DatabaseConnection;
import com.barbearia.view.MenuView;

public class App {
    public static void main(String[] args) {
        try {
            DatabaseConnection.inicializarBancoDeDados();
            new MenuView().iniciar();
        } catch (Exception e) {
            System.out.println("Erro ao iniciar o sistema: " + e.getMessage());
        }
    }
}