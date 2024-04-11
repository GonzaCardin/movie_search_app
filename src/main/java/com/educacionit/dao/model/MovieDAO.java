package com.educacionit.dao.model;

import com.educacionit.model.Movie;
import java.util.List;

public interface MovieDAO {
    public Movie searchById(Integer id);
    public List<Movie> searchAll();

    public Integer save(Movie newMovie);
    public void update(Movie movie);
    public void delete(Integer id);
}
