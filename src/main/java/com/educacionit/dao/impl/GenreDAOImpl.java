package com.educacionit.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.educacionit.dao.database.ConnectionDB;
import com.educacionit.dao.model.GenreDAO;
import com.educacionit.model.Genre;

public class GenreDAOImpl implements GenreDAO, ConnectionDB {

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        String selectQuery = "SELECT * FROM genres";
        try (Connection conn = getConnection()) {
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            ResultSet rs = selectStatement.executeQuery();

            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getInt("id"));
                genre.setName(rs.getString("genre"));
                genres.add(genre);
            }
            return genres;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void addGenre(String idMovie, String Genre) {
        try (Connection conn = getConnection()) {
            String insertQuery = "INSERT INTO genres (movie_id, genre) VALUES(?,?)";
            PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
            insertStatement.setString(1, idMovie);
            insertStatement.setString(2, Genre);
            insertStatement.executeUpdate();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public void deleteGenre(String idMovie) {
        try (Connection conn = getConnection()) {
            String deleteQuery = "DELETE FROM genres WHERE movie_id =?";
            PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery);

            deleteStatement.setString(1, idMovie);
            deleteStatement.executeUpdate();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public List<String> getGenresByIdMovie(String idMovie) {
        List<String> genres = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String selectQuery = "SELECT genre FROM genres WHERE movie_id = ?";
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            selectStatement.setString(1, idMovie);
            ResultSet rs = selectStatement.executeQuery();
            while (rs.next()) {
                genres.add(rs.getString("genre"));
            }
            return genres;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

}
