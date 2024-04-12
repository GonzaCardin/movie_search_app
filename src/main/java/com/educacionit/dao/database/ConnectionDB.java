package com.educacionit.dao.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface ConnectionDB {
    default Connection getConnection() throws SQLException{

        try {
            final String URL = "jdbc:mysql://localhost/films";
            final String USER = "root";
            final String PASSWORD = "Jacques";

            Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
            
        return conn;
        } catch (Exception e) {
            throw new SQLException(e.getMessage(), e);
        }
    }
}
