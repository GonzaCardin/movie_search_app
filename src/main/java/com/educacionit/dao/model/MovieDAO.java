package com.educacionit.dao.model;

import com.educacionit.model.Movie;

import java.sql.SQLException;
import java.util.List;

public interface MovieDAO {
    public Movie searchById(String id) throws SQLException;
    public List<Movie> searchAll();

    public String save(Movie newMovie);
    public void update(Movie movie);
    public void delete(String id);
}
