package com.educacionit.dao.model;

import java.util.List;

import com.educacionit.model.Genre;

public interface GenreDAO {
    List<Genre> getAllGenres();
    void addGenre(String idMovie, String Genre);
    void deleteGenre(String idMovie);
    List<String> getGenresByIdMovie(String idMovie);
}
