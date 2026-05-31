package com.barbearia.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:barbearia.db";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL);
        }
        return connection;
    }

    public static void inicializarBancoDeDados() throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS barbearia (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nomeEstabelecimento TEXT NOT NULL
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS barbeiro (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    login TEXT NOT NULL UNIQUE,
                    senha TEXT NOT NULL,
                    horarioInicio TEXT NOT NULL,
                    horarioFim TEXT NOT NULL,
                    ativo INTEGER NOT NULL DEFAULT 1
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS servico (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    descricao TEXT,
                    preco REAL NOT NULL,
                    duracaoEmMinutos INTEGER NOT NULL
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS agendamento (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nomeCliente TEXT NOT NULL,
                    contatoCliente TEXT NOT NULL,
                    dataHora TEXT NOT NULL,
                    status TEXT NOT NULL,
                    idBarbeiro INTEGER NOT NULL,
                    idServico INTEGER NOT NULL,
                    FOREIGN KEY (idBarbeiro) REFERENCES barbeiro(id),
                    FOREIGN KEY (idServico) REFERENCES servico(id)
                )
            """);
        }
    }
}