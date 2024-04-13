package com.educacionit.dao.model;

import com.educacionit.exceptions.DBException;
import com.educacionit.exceptions.MovieException;
import com.educacionit.model.Movie;


import java.util.List;

public interface MovieDAO {
    public Movie searchById(String id) throws DBException, MovieException;

    public Movie searchByTitle(String title) throws DBException, MovieException;

    public List<Movie> searchAll() throws DBException, MovieException;

    public String save(Movie newMovie) throws DBException, MovieException;

    public void update(Movie movie) throws DBException, MovieException;

    public void delete(String id) throws DBException, MovieException;

    List<Movie> searchByGenre(String genre) throws DBException, MovieException;
}
