package com.educacionit.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.educacionit.dao.database.ConnectionDB;
import com.educacionit.dao.model.MovieDAO;
import com.educacionit.exceptions.DBException;
import com.educacionit.exceptions.MovieException;
import com.educacionit.model.Genre;
import com.educacionit.model.Movie;

public class MovieDAOImpl implements MovieDAO, ConnectionDB {
    private Connection conn;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Movie searchById(String id) throws DBException, MovieException {
        conn = null;
        try {
            conn = getConnection();
            String query = "SELECT m.id, m.title , m.siteUrl, m.imageUrl, g.id, g.genre FROM movies m INNER JOIN genres g ON m.id = g.movie_id WHERE  m.id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            Movie movie = new Movie();

            if (rs.next()) {
                movie.setId(rs.getString("m.id"));
                movie.setName(rs.getString("m.title"));
                movie.setOfficialSiteUrl(rs.getString("m.siteUrl"));
                movie.setImageUrl(rs.getString("m.imageUrl"));

                List<Genre> genres = new ArrayList();
                do {
                    Genre genre = new Genre();
                    genre.setId(rs.getInt("g.id"));
                    genre.setName(rs.getString("g.genre"));
                    genres.add(genre);
                } while (rs.next());
                movie.setGenres(genres);

            }
            return movie;
        } catch (SQLException e) {
            throw new MovieException(MovieException.ERROR_1, "It was not possible to find the movie with id: " + id, e);
        } finally {
            closeConnection(conn);
        }

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<Movie> searchAll() throws DBException, MovieException  {
        String query = "SELECT m.id, m.title , m.siteUrl, m.imageUrl, g.id, g.genre FROM movies m INNER JOIN genres g ON m.id = g.movie_id";
        List<Movie> movies = new ArrayList<>();
        conn = null;
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getString("id"));
                movie.setName(rs.getString("title"));
                movie.setOfficialSiteUrl("siteUrl");
                movie.setImageUrl(rs.getString("imageUrl"));
                List<Genre> genres = new ArrayList();
                do {
                    Genre genre = new Genre();
                    genre.setId(rs.getInt("id"));
                    genre.setName(rs.getString("genre"));
                    genres.add(genre);
                } while (rs.next());
                movie.setGenres(genres);

            }
            return movies;
        } catch (SQLException e) {
            throw new MovieException(MovieException.ERROR_2, "It was not possible to show all the movies. Error: " + e.getLocalizedMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    @Override
    public String save(Movie newMovie) throws DBException, MovieException {
        String movieId = newMovie.getId();
        conn = null;
        try {
            conn = getConnection();
            String movieQuery = "INSERT INTO movies (id,title,siteURL,imageURL) VALUES (?,?,?,?)";
            PreparedStatement m_statement = conn.prepareStatement(movieQuery);

            m_statement.setString(1, movieId);
            m_statement.setString(2, newMovie.getName());
            m_statement.setString(3, newMovie.getOfficialSiteUrl());
            m_statement.setString(4, newMovie.getImageUrl());
            m_statement.executeUpdate();

            String genreQuery = "INSERT INTO genres (movie_id,genre)";
            PreparedStatement g_statement = conn.prepareStatement(genreQuery);

            for (Genre genre : newMovie.getGenres()) {
                g_statement.setString(1, movieId);
                g_statement.setString(2, genre.getName());
                g_statement.executeUpdate();
            }
            return movieId;
        } catch (SQLException e) {
            throw new MovieException(MovieException.ERROR_3, "It was not possible to add the movie: " + newMovie.getName(), e);
        } finally {
            closeConnection(conn);
        }
    }

    @Override
    public void update(Movie movie) throws DBException, MovieException {
        conn = null;
        try {
            conn = getConnection();
            String updateQuery = "UPDATE movies SET title =?, siteURL =?, imageURL =? WHERE id =?";
            PreparedStatement m_statement = conn.prepareStatement(updateQuery);
            m_statement.setString(1, movie.getName());
            m_statement.setString(2, movie.getOfficialSiteUrl());
            m_statement.setString(3, movie.getImageUrl());
            m_statement.setString(4, movie.getId());
            m_statement.executeUpdate();

            String deleteGenreQuery = "DELETE FROM genres WHERE movie_id =?";
            PreparedStatement delete_g_statement = conn.prepareStatement(deleteGenreQuery);
            delete_g_statement.setString(1, movie.getId());
            delete_g_statement.executeUpdate();

            String insertGenreQuery = "INSERT INTO genres (movie_id,genre) VALUES(?, ?)";
            PreparedStatement insert_g_statement = conn.prepareStatement(insertGenreQuery);
            for (Genre genre : movie.getGenres()) {
                insert_g_statement.setString(1, movie.getId());
                insert_g_statement.setString(2, genre.getName());
                insert_g_statement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new MovieException(MovieException.ERROR_4, "It was not possible to modify the movie: " + movie.getName(), e);
        } finally {
            closeConnection(conn);
        }
    }

    @Override
    public void delete(String id) throws DBException, MovieException {
        conn = null;
        try {
            conn = getConnection();
            String deleteMovieQuery = "DELETE FROM movies WHERE id = ?";
            String deleteGenresQuery = "DELETE FROM genres WHERE movie_id = ?";
            PreparedStatement delete_m_statement = conn.prepareStatement(deleteMovieQuery);
            delete_m_statement.setString(1, id);
            delete_m_statement.executeUpdate();

            PreparedStatement delete_g_statement = conn.prepareStatement(deleteGenresQuery);
            delete_g_statement.setString(1, id);
            delete_g_statement.executeUpdate();

        } catch (Exception e) {
            throw new MovieException(MovieException.ERROR_5, "It was not possible to delete the movie with ID: " + id, e);
        } finally {
            closeConnection(conn);
        }
    }

}
