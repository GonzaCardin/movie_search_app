package com.educacionit.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.educacionit.dao.database.ConnectionDB;
import com.educacionit.dao.model.GenreDAO;
import com.educacionit.exceptions.DBException;
import com.educacionit.exceptions.GenreException;
import com.educacionit.exceptions.MovieException;
import com.educacionit.model.Genre;

public class GenreDAOImpl implements GenreDAO, ConnectionDB {
    Connection conn;

    @Override
    public List<Genre> getAllGenres() throws DBException, GenreException {
        List<Genre> genres = new ArrayList<>();
        String selectQuery = "SELECT * FROM genres";
        conn = null;
        try {
            conn = getConnection();
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            ResultSet rs = selectStatement.executeQuery();

            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getInt("id"));
                genre.setName(rs.getString("genre"));
                genres.add(genre);
            }
            return genres;
        } catch (SQLException e) {
            throw new GenreException(GenreException.ERROR_1,
                    "It was not possible to displaying all the movie genres. Error: " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    @Override
    public void addGenre(String idMovie, String genre) throws DBException, MovieException {
        conn = null;
        try {
            conn = getConnection();
            String insertQuery = "INSERT INTO genres (movie_id, genre) VALUES(?,?)";
            PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
            insertStatement.setString(1, idMovie);
            insertStatement.setString(2, genre);
            insertStatement.executeUpdate();

        } catch (SQLException e) {
            throw new MovieException(GenreException.ERROR_2, "It was not possible to add the genre " + genre
                    + " to the movie with ID: " + idMovie + ". Error: " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    @Override
    public void deleteGenre(String idMovie) throws DBException, GenreException {
        conn = null;
        try {
            conn = getConnection();
            String deleteQuery = "DELETE FROM genres WHERE movie_id =?";
            PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery);

            deleteStatement.setString(1, idMovie);
            deleteStatement.executeUpdate();

        } catch (SQLException e) {
            throw new GenreException(GenreException.ERROR_3, "It was not possible to delete the genres from the movie with ID: " + idMovie +". Error: "+e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    @Override
    public List<String> getGenresByIdMovie(String idMovie) throws DBException, GenreException {
        List<String> genres = new ArrayList<>();
        conn = null;
        try {
            conn = getConnection();
            String selectQuery = "SELECT genre FROM genres WHERE movie_id = ?";
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            selectStatement.setString(1, idMovie);
            ResultSet rs = selectStatement.executeQuery();
            while (rs.next()) {
                genres.add(rs.getString("genre"));
            }
            return genres;
        } catch (SQLException e) {
            throw new GenreException(GenreException.ERROR_4, "It was not possible to show the genres of the movie with ID: " + idMovie+ ". Error: "+ e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

}
