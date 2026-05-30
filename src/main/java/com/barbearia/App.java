package com.barbearia;

import com.barbearia.database.DatabaseConnection;

public class App {
    public static void main(String[] args) {
        try {
            DatabaseConnection.inicializarBancoDeDados();
            System.out.println("Banco de dados iniciado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
