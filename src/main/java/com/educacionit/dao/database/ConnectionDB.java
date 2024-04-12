package com.educacionit.dao.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.educacionit.exceptions.DBException;

public interface ConnectionDB {
    default Connection getConnection() throws DBException {

        try {
            final String URL = "jdbc:mysql://localhost/films";
            final String USER = "root";
            final String PASSWORD = "Jacques";

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            return conn;
        } catch (SQLException e) {
            throw new DBException(DBException.ERROR_1, "Unable to connect to the database. Error: " + e.getMessage(),
                    e);
        }
    }

    default void closeConnection(Connection conn) throws DBException {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DBException(DBException.ERROR_2,
                        "Unable to disconnect from the database. Error: " + e.getMessage(), e);
            }
        }
    }

}
