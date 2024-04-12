package com.educacionit.service;

import com.educacionit.model.Movie;

import java.sql.SQLException;
import java.util.List;

public interface MovieService {
    Movie searchMovieById(String id) throws SQLException;
    List<Movie> searchAllMovies();
    String addMovie(Movie newMovie);
    void updateMovie(Movie movie);
    void deleteMovie(String id);
    
    List<String> getGenresByMovieId(String movieId);
    void addGenreToMovie(String movieId, String genre);
    void deleteGenreByMovieId(String movidId);
}
