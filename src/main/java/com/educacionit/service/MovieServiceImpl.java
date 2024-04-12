package com.educacionit.service;

import java.sql.SQLException;
import java.util.List;

import com.educacionit.dao.impl.GenreDAOImpl;
import com.educacionit.dao.impl.MovieDAOImpl;
import com.educacionit.dao.model.GenreDAO;
import com.educacionit.dao.model.MovieDAO;
import com.educacionit.model.Movie;

public class MovieServiceImpl implements MovieService {
    private MovieDAO movieDAO = new MovieDAOImpl();
    private GenreDAO genreDAO = new GenreDAOImpl();
    @Override
    public Movie searchMovieById(String id) throws SQLException {
       return movieDAO.searchById(id);
    }

    @Override
    public List<Movie> searchAllMovies() {
        return movieDAO.searchAll();
    }

    @Override
    public String addMovie(Movie newMovie) {
        return movieDAO.save(newMovie);
    }

    @Override
    public void updateMovie(Movie movie) {
        movieDAO.update(movie);
    }

    @Override
    public void deleteMovie(String id) {
        movieDAO.delete(id);
    }

    @Override
    public List<String> getGenresByMovieId(String movieId) {
        return genreDAO.getGenresByIdMovie(movieId);
    }

    @Override
    public void addGenreToMovie(String movieId, String genre) {
        genreDAO.addGenre(movieId, genre);
    }

    @Override
    public void deleteGenreByMovieId(String movidId) {
        genreDAO.addGenre(movidId, movidId);
    }

}
