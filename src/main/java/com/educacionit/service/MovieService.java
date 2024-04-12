package com.educacionit.service;

import com.educacionit.exceptions.DBException;
import com.educacionit.exceptions.GenreException;
import com.educacionit.exceptions.MovieException;
import com.educacionit.model.Movie;

import java.sql.SQLException;
import java.util.List;

public interface MovieService {
    Movie searchMovieById(String id) throws SQLException, DBException, MovieException;

    List<Movie> searchAllMovies() throws DBException, MovieException;

    String addMovie(Movie newMovie) throws DBException, MovieException;

    void updateMovie(Movie movie) throws DBException, MovieException;

    void deleteMovie(String id) throws DBException, MovieException;

    List<Movie> searchMoviesByGenre(String genre) throws DBException,MovieException;

    List<String> getGenresByMovieId(String movieId) throws DBException, GenreException;

    void addGenreToMovie(String movieId, String genre) throws DBException, MovieException;

    void deleteGenreByMovieId(String movidId) throws DBException, MovieException;
}
