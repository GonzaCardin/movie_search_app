package com.educacionit.service;

import java.sql.SQLException;
import java.util.List;

import com.educacionit.dao.impl.GenreDAOImpl;
import com.educacionit.dao.impl.MovieDAOImpl;
import com.educacionit.dao.model.GenreDAO;
import com.educacionit.dao.model.MovieDAO;
import com.educacionit.exceptions.DBException;
import com.educacionit.exceptions.GenreException;
import com.educacionit.exceptions.MovieException;
import com.educacionit.model.Movie;

public class MovieServiceImpl implements MovieService {
    private MovieDAO movieDAO = new MovieDAOImpl();
    private GenreDAO genreDAO = new GenreDAOImpl();

    @Override
    public Movie searchMovieById(String id) throws SQLException, DBException, MovieException {
        return movieDAO.searchById(id);
    }
    

    @Override
    public Movie searchMovieByTitle(String tString) throws SQLException, DBException, MovieException {
        return movieDAO.searchByTitle(tString);
    }


    @Override
    public List<Movie> searchAllMovies() throws DBException, MovieException {
        return movieDAO.searchAll();
    }

    @Override
    public String addMovie(Movie newMovie) throws DBException, MovieException {
        return movieDAO.save(newMovie);
    }

    @Override
    public void updateMovie(Movie movie) throws DBException, MovieException {
        movieDAO.update(movie);
    }

    @Override
    public void deleteMovie(String id) throws DBException, MovieException {
        movieDAO.delete(id);
    }

    @Override
    public List<String> getGenresByMovieId(String movieId) throws DBException, GenreException {
        return genreDAO.getGenresByIdMovie(movieId);
    }

    @Override
    public void addGenreToMovie(String movieId, String genre) throws DBException, MovieException {
        genreDAO.addGenre(movieId, genre);
    }

    @Override
    public void deleteGenreByMovieId(String movidId) throws DBException, MovieException {
        genreDAO.addGenre(movidId, movidId);
    }

    @Override
    public List<Movie> searchMoviesByGenre(String genre) throws DBException, MovieException {
        return movieDAO.searchByGenre(genre);
    }
    

}
