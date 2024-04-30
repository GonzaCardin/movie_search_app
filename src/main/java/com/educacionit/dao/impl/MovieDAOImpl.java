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

    @Override
    public Movie searchById(String id) throws DBException, MovieException {
        conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT m.id, m.title , m.siteUrl, m.imageUrl, g.id, g.genre FROM movies m INNER JOIN genres g ON m.id = g.movie_id WHERE  m.id = ?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, id);

            rs = ps.executeQuery();
            Movie movie = new Movie();

            if (rs.next()) {
                movie.setId(rs.getString("m.id"));
                movie.setName(rs.getString("m.title"));
                movie.setOfficialSiteUrl(rs.getString("m.siteUrl"));
                movie.setImageUrl(rs.getString("m.imageUrl"));

                List<Genre> genres = new ArrayList<>();
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
            try {
                rs.close();
            } catch (Exception e) {
                System.err.println("It was not possible to close the result set");
            }
            try {
                ps.close();
            } catch (Exception e) {
                System.err.println("It was not possible to close the statement");
            }
        }

    }

    @Override
    public List<Movie> searchAll() throws DBException, MovieException {
        String query = "SELECT m.id, m.title , m.siteUrl, m.imageUrl, g.id, g.genre FROM movies m INNER JOIN genres g ON m.id = g.movie_id";
        List<Movie> movies = new ArrayList<>();
        conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getString("id"));
                movie.setName(rs.getString("title"));
                movie.setOfficialSiteUrl("siteUrl");
                movie.setImageUrl(rs.getString("imageUrl"));
                List<Genre> genres = new ArrayList<>();
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
            throw new MovieException(MovieException.ERROR_2,
                    "It was not possible to show all the movies. Error: " + e.getLocalizedMessage(), e);
        } finally {
            closeConnection(conn);
            try {
                rs.close();
            } catch (Exception e) {
                System.err.println("It was not possible to close the result set");
            }
            try{
                ps.close();
            }catch(Exception e){
                System.err.println("It was not possible to close the result set");
            }
        }
    }

    @Override
    public String save(Movie newMovie) throws DBException, MovieException {
        String movieId = newMovie.getId();
        String movieQuery = "INSERT INTO movies (id,title,siteURL,imageURL,image) VALUES (?,?,?,?,?)";
        conn = null;
        PreparedStatement m_statement = null;
        PreparedStatement g_statement = null;
        try {
            conn = getConnection();
            m_statement = conn.prepareStatement(movieQuery);

            m_statement.setString(1, movieId);
            m_statement.setString(2, newMovie.getName());
            m_statement.setString(3, newMovie.getOfficialSiteUrl());
            m_statement.setString(4, newMovie.getImageUrl());
            m_statement.setBytes(5, newMovie.getImage());
            m_statement.executeUpdate();

            String genreQuery = "INSERT INTO genres (movie_id,genre) VALUES (?,?)";
            g_statement = conn.prepareStatement(genreQuery);

            for (Genre genre : newMovie.getGenres()) {
                g_statement.setString(1, movieId);
                g_statement.setString(2, genre.getName());
                g_statement.executeUpdate();
            }
            return movieId;
        } catch (SQLException e) {
            throw new MovieException(MovieException.ERROR_3,
                    "It was not possible to add the movie: " + newMovie.getName(), e);
        } finally {
            closeConnection(conn);
            try {
                m_statement.close();
            } catch (Exception e) {
                System.err.println("It was not possible to close the statement");
            }
            try {
                g_statement.close();
            } catch (Exception e) {
                System.err.println("It was not possible to close the statement");
            }
        }
    }

    @Override
    public void update(Movie movie) throws DBException, MovieException {
        
        conn = null;
        String updateQuery = "UPDATE movies SET title =?, siteURL =?, imageURL =?, image=? WHERE id =?";
        PreparedStatement m_statement = null;
        PreparedStatement delete_g_statement = null;
        PreparedStatement insert_g_statement = null;
        try {
            conn = getConnection();
            m_statement = conn.prepareStatement(updateQuery);
            m_statement.setString(1, movie.getName());
            m_statement.setString(2, movie.getOfficialSiteUrl());
            m_statement.setString(3, movie.getImageUrl());
            m_statement.setBytes(4, movie.getImage());
            m_statement.setString(5, movie.getId());
            m_statement.executeUpdate();

            String deleteGenreQuery = "DELETE FROM genres WHERE movie_id =?";
            delete_g_statement = conn.prepareStatement(deleteGenreQuery);
            delete_g_statement.setString(1, movie.getId());
            delete_g_statement.executeUpdate();

            String insertGenreQuery = "INSERT INTO genres (movie_id,genre) VALUES(?, ?)";
            insert_g_statement = conn.prepareStatement(insertGenreQuery);
            for (Genre genre : movie.getGenres()) {
                insert_g_statement.setString(1, movie.getId());
                insert_g_statement.setString(2, genre.getName());
                insert_g_statement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new MovieException(MovieException.ERROR_4,
                    "It was not possible to modify the movie: " + movie.getName(), e);
        } finally {
            closeConnection(conn);
            try {
                m_statement.close();
            } catch (Exception e) {
                System.err.println("It was not possible to close the statement");
            }
            try {
                delete_g_statement.close();
            } catch (Exception e) {
                System.err.println("It was not possible to close de statement");
            }
            try {
                insert_g_statement.close();
            } catch (Exception e) {
                System.err.println("It was not possible to close de statement");
            }
        }
    }

    @Override
    public void delete(String id) throws DBException, MovieException {
        conn = null;
        String deleteMovieQuery = "DELETE FROM movies WHERE id = ?";
        String deleteGenresQuery = "DELETE FROM genres WHERE movie_id = ?";
        PreparedStatement delete_m_statement = null;
        PreparedStatement delete_g_statement = null;
        try {
            conn = getConnection();

            delete_g_statement = conn.prepareStatement(deleteGenresQuery);
            delete_g_statement.setString(1, id);
            delete_g_statement.executeUpdate();

            delete_m_statement = conn.prepareStatement(deleteMovieQuery);
            delete_m_statement.setString(1, id);
            delete_m_statement.executeUpdate();
        } catch (SQLException e) {
            throw new MovieException(MovieException.ERROR_5, "It was not possible to delete the movie with ID: " + id,
                    e);
        } finally {
            closeConnection(conn);
            try {
                delete_g_statement.close();
            } catch (Exception e) {
                System.err.println("It was not possible to close de statement");
            }
            try {
                delete_m_statement.close();
            } catch (Exception e) {
                System.err.println("It was not possible to close de statement");
            }
        }
    }

    @Override
    public List<Movie> searchByGenre(String genre) throws DBException, MovieException {
        conn = null;
        List<Movie> moviesByGenre = new ArrayList<>();
        String searchByGenreQuery = "SELECT m.id, m.title, m.siteUrl, m.imageUrl FROM movies m INNER JOIN genres g ON m.id = g.movie_id WHERE g.genre = ?";
        PreparedStatement search_g_statement = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            search_g_statement = conn.prepareStatement(searchByGenreQuery);
            search_g_statement.setString(1, genre);
            rs = search_g_statement.executeQuery();

            while (rs.next()) {
                Movie m = new Movie();
                m.setId(rs.getString("id"));
                m.setName(rs.getString("title"));
                m.setOfficialSiteUrl(rs.getString("siteUrl"));
                m.setImageUrl(rs.getString("imageUrl"));
                moviesByGenre.add(m);
            }

            return moviesByGenre;
        } catch (SQLException e) {
            throw new MovieException(MovieException.ERROR_6,
                    "It was not possible to find a movie by the genre: " + genre, e);
        } finally {
            closeConnection(conn);
            try {
                rs.close();
            } catch (Exception e) {
                System.err.println("It was not possible to close the result set.");
            }
            try {
                search_g_statement.close();
            } catch (Exception e) {
                System.err.println("It was not possible to close the statement.");
            }
        }
    }

    @Override
    public Movie searchByTitle(String title) throws DBException, MovieException {
        String searchByTitleQuery = "SELECT m.id, m.title , m.siteUrl, m.imageUrl, g.id, g.genre FROM movies m INNER JOIN genres g ON m.id = g.movie_id WHERE  m.title = ?";
        conn = null;
        PreparedStatement search_m_statement = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            search_m_statement = conn.prepareStatement(searchByTitleQuery);
            search_m_statement.setString(1, title);
            rs = search_m_statement.executeQuery();

            Movie movie = new Movie();

            if (rs.next()) {
                movie.setId(rs.getString("m.id"));
                movie.setName(rs.getString("m.title"));
                movie.setOfficialSiteUrl(rs.getString("m.siteUrl"));
                movie.setImageUrl(rs.getString("m.imageUrl"));

                List<Genre> genres = new ArrayList<>();
                do {
                    Genre genre = new Genre();
                    genre.setId(rs.getInt("g.id"));
                    genre.setName(rs.getString("g.genre"));
                    genres.add(genre);
                } while (rs.next());
                movie.setGenres(genres);

                return movie;
            }
        } catch (SQLException e) {
            throw new MovieException(MovieException.ERROR_7, "It was not possible to find the movie in the database",
                    e);
        } finally {
            closeConnection(conn);
            try {
                rs.close();
            } catch (Exception e) {
                System.err.println("It was not possible to close the result set.");
            }
            try {
                search_m_statement.close();
            } catch (Exception e) {
                System.err.println("It was not possible to close the statement.");
            }
        }
        return null;

    }

}
