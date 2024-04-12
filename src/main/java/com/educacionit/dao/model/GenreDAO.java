package com.educacionit.dao.model;

import java.util.List;

import com.educacionit.exceptions.DBException;
import com.educacionit.exceptions.GenreException;
import com.educacionit.exceptions.MovieException;
import com.educacionit.model.Genre;

public interface GenreDAO {
    List<Genre> getAllGenres() throws DBException, GenreException;

    void addGenre(String idMovie, String Genre) throws DBException, MovieException;

    void deleteGenre(String idMovie) throws DBException, GenreException;

    List<String> getGenresByIdMovie(String idMovie) throws DBException, GenreException;
}
