package com.educacionit.main;

import java.util.Scanner;
import java.sql.SQLException;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.educacionit.exceptions.DBException;
import com.educacionit.exceptions.MovieException;
import com.educacionit.model.Genre;
import com.educacionit.model.Movie;
import com.educacionit.service.MovieService;
import com.educacionit.service.MovieServiceImpl;

public class MainMenu {
    private static Scanner scann = new Scanner(System.in);
    private static MovieService mS = new MovieServiceImpl();

    public static void main(String[] args) throws SQLException, DBException, MovieException {
        
            System.out.println("=== MOVIE SEARCH APP ===");
            System.out.println("1. Search Movies ");
            System.out.println("2. Add New Movie");
            System.out.println("3. Update Movie");
            System.out.println("4. Delete Movie");
            System.out.println("5. Exit");

            System.out.println("Enter your choice: ");
            int choice = scann.nextInt();
            scann.nextLine();

            switch (choice) {
                case 1:
                    searchMovies();
                    break;
                case 2:
                    addNewMovie();
                    break;
                case 3:
                    updateMovie();
                    break;
                case 4:
                    deleteMovie();
                    break;
                case 5:
                    System.out.println("See you soon. Exiting......");
                    return;
                default:
                    System.out.println("Invalid Choice! Try again.");
            }
        

    }

    private static void searchMovies() throws SQLException, DBException, MovieException {
        System.out.println("Enter search criteria(Title - Genre): ");
        String searchChoice = scann.next().toLowerCase();
        scann.nextLine();

        if (searchChoice.equals("title")) {
            System.out.println("Enter movie ID: ");
            String id = scann.next();
            Movie movie = mS.searchMovieById(id);
            System.out.println(movie);
        } else if (searchChoice.equals("genre")) {
            String genre = scann.next();
            List<Movie> moviesByGenre = mS.searchMoviesByGenre(genre);
            moviesByGenre.forEach(System.out::println);
        }
        return;
    }

    private static void addNewMovie() throws DBException, MovieException {
        System.out.println("Enter movie title: ");
        String title = scann.nextLine();
        System.out.println("Enter movie ID: ");
        String id = scann.nextLine();
        System.out.println("Enter movie site URL: ");
        String site = scann.nextLine();
        System.out.println("Enter movie image URL: ");
        String imageUrl = scann.nextLine();
        System.out.println("Enter movie genres(separated by comma): ");
        String genreInput = scann.nextLine();
        List<Genre> genres = Arrays.stream(genreInput.split(","))
                .map(genreName -> new Genre(genreName.trim()))
                .collect(Collectors.toList());

        Movie m = new Movie(id, title, site, imageUrl, genres);

        try {
            String movieId = mS.addMovie(m);
            System.out.println("Movie added successfully with ID: " + movieId);
        } catch (DBException | MovieException e) {
            System.err.println("Error adding movie: " + e.getMessage());
        }
    }
    
    private static void updateMovie() throws SQLException {
        System.out.println("Enter the movie ID to update: ");
        String idMovieUpd = scann.nextLine();
        try {
            Movie existingMovie = mS.searchMovieById(idMovieUpd);
            if (existingMovie != null) {
                System.out.println("Current movie details: ");
                System.out.println("Title: " + existingMovie.getName());
                System.out.println("Site URL: " + existingMovie.getOfficialSiteUrl());
                System.out.println("Images URL: " + existingMovie.getImageUrl());
                System.out.println("Genres: "
                + existingMovie.getGenres().stream().map(Genre::getName).collect(Collectors.joining(", ")));
                
                System.out.println("------------------------------------------------");
                
                System.out.println("Enter new movie details or leave blank to keep current:");
                
                System.out.println("Title: ");
                String newTitle = scann.nextLine().trim();
                newTitle = newTitle.isEmpty() ? existingMovie.getName() : newTitle;
                
                System.out.println("ID: ");
                String newID = scann.nextLine().trim();
                newID = newID.isEmpty() ? existingMovie.getId() : newID;
                
                System.out.println("Site URL: ");
                String newSiteUrl = scann.nextLine().trim();
                newSiteUrl = newSiteUrl.isEmpty() ? existingMovie.getOfficialSiteUrl() : newSiteUrl;
                
                System.out.println("Image URL: ");
                String newImageUrl = scann.nextLine().trim();
                newImageUrl = newImageUrl.isEmpty() ? existingMovie.getImageUrl() : newImageUrl;
                
                System.out.println("Genres(separated by comma): ");
                String genresInput = scann.nextLine();
                List<Genre> newGenres = new ArrayList<>();
                if (!genresInput.isEmpty()) {
                    newGenres = Arrays.stream(genresInput.split(","))
                    .map(genreName -> new Genre(genreName.trim()))
                    .collect(Collectors.toList());
                }else{
                    newGenres = existingMovie.getGenres();
                }
                Movie updateMovie = new Movie(newID, newTitle, newSiteUrl, newImageUrl, newGenres);
                
                mS.updateMovie(updateMovie);
                System.out.println("Movie updated successfully.");
            }
        } catch (DBException | MovieException e) {
            System.err.println("Error updating movie: " + e.getMessage());
        }
    }
    private static void deleteMovie() throws SQLException{
        System.out.println("Enter the ID movie to delete:");
        String idMovie = scann.nextLine();

        try {
            Movie existingMovie = mS.searchMovieById(idMovie);
            if(existingMovie != null){
                System.out.println("Are you sure you want to delete the movie " + existingMovie.getName() + " ? (yes or no)");
                String confirmation = scann.nextLine();
                if (confirmation.equalsIgnoreCase("yes")) {
                    mS.deleteMovie(idMovie);
                    System.out.println("The movie " + existingMovie.getName() + " has been successfully deleted.");
                }
                else{
                    System.out.println("Deletion Canceled.");
                }
            }else{
                System.out.println("Movie with ID: " + idMovie +" was not found.");
            }
        } catch (DBException | MovieException e) {
            System.err.println("Error deleting movie: " + e.getMessage());
        }
    }
}
