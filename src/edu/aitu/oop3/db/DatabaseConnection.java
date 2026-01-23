package edu.aitu.oop3.db;

import interfaces.IDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection implements IDB {
    private static final String URL =
            "jdbc:postgresql://aws-1-ap-northeast-1.pooler.supabase.com:5432/postgres?sslmode=require";
    private static final String USER = "postgres.qepbsaknuarilyswpvzl";
    private static final String PASSWORD = "bevdib-zUsmug-sebwo5";

    private static DatabaseConnection instance; /* Singleton Pattern:checks so we have
     only ONE DatabaseConnection obj in entire program*/

    private DatabaseConnection() {
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}