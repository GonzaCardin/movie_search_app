package com.educacionit.main;

import java.util.Scanner;
import java.io.IOException;
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
import com.educacionit.utils.FileManager;

public class MainMenu {
    private static Scanner scann = new Scanner(System.in);
    private static MovieService mS = new MovieServiceImpl();
    private static FileManager imageFile = new FileManager();

    public static void main(String[] args) throws SQLException, DBException, MovieException, IOException {

        boolean exitClause = false;

        while (!exitClause) {
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
                    exitClause = true;
                    break;
                default:
                    System.out.println("Invalid Choice! Try again.");
            }
        }

    }

    private static void searchMovies() throws SQLException, DBException, MovieException {
        System.out.println("Enter search criteria(Title - Genre): ");
        String searchChoice = scann.next();
        scann.nextLine();

        if (searchChoice.equals("title")) {
            System.out.println("Enter movie title: ");
            String title = scann.nextLine();
            Movie movie = mS.searchMovieByTitle(title);
            displayMovieDetails(movie.getId());
        } else if (searchChoice.equals("genre")) {
            System.out.println("Enter movie genre: ");
            String genre = scann.next();
            List<Movie> moviesByGenres = mS.searchMoviesByGenre(genre);
            displayMovies(moviesByGenres);
            System.out.println("Enter movie ID to see more information: ");
            String movieId = scann.next();
            displayMovieDetails(movieId);
        }
        return;
    }

    private static void displayMovies(List<Movie> movies) {
        System.out.println("=== Movie List ===");
        for (Movie movie : movies) {
            System.out.println("ID: " + movie.getId() + ", Title: " + movie.getName());
        }
    }

    public static void displayMovieDetails(String idMovie) {
        try {
            Movie movie = mS.searchMovieById(idMovie);
            if (movie != null) {
                System.out.println("=== Movie Details ===");
                System.out.println("ID: " + movie.getId() + ", Title: " + movie.getName());
                System.out.println("Site: " + movie.getOfficialSiteUrl());
                System.out.println("Image URL: " + movie.getImageUrl());
                System.out.println("Genres: ");
                for (Genre genre : movie.getGenres()) {
                    System.out.println(genre.getName());
                }
            } else {
                System.out.println("Movie with ID: " + idMovie + " not found!");
            }
        } catch (Exception e) {
            System.err.println("Error getting movie details: " + e.getMessage());
        }

    }

    private static void addNewMovie() throws DBException, MovieException, IOException {
        System.out.println("Enter movie title: ");
        String title = scann.nextLine();
        System.out.println("Enter movie ID: ");
        String id = scann.nextLine();
        System.out.println("Enter movie site URL: ");
        String site = scann.nextLine();
        System.out.println("Enter movie image path: ");
        String imageUrl = scann.nextLine();

        if (imageFile.fileExists(imageUrl)) {
            byte[] image = imageFile.readImage(imageUrl);
            System.out.println("Enter movie genres(separated by comma): ");
            String genreInput = scann.nextLine();
            List<Genre> genres = Arrays.stream(genreInput.split(","))
                    .map(genreName -> new Genre(genreName.trim()))
                    .collect(Collectors.toList());
            Movie m = new Movie(id, title, site, imageUrl, image, genres);
            try {
                String movieId = mS.addMovie(m);
                System.out.println("Movie added successfully with ID: " + movieId);
            } catch (DBException | MovieException e) {
                System.err.println("Error adding movie: " + e.getMessage());
            }
        }

    }

    private static void updateMovie() throws SQLException, IOException {
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

                System.out.println("Site URL: ");
                String newSiteUrl = scann.nextLine().trim();
                newSiteUrl = newSiteUrl.isEmpty() ? existingMovie.getOfficialSiteUrl() : newSiteUrl;

                System.out.println("Image Path: ");
                String newImageUrl = scann.nextLine().trim();
                newImageUrl = newImageUrl.isEmpty() ? existingMovie.getImageUrl() : newImageUrl;

                byte newImage[] = existingMovie.getImage();             
                
                if(!newImageUrl.isEmpty()){
                    if(imageFile.fileExists(newImageUrl)){
                        newImage = imageFile.readImage(newImageUrl);
                    }else{
                        System.err.println("Image file does not exist.");
                    }
                }

                System.out.println("Genres(separated by comma): ");
                String genresInput = scann.nextLine();
                List<Genre> newGenres = new ArrayList<>();
                if (!genresInput.isEmpty()) {
                    newGenres = Arrays.stream(genresInput.split(","))
                            .map(genreName -> new Genre(genreName.trim()))
                            .collect(Collectors.toList());
                } else {
                    newGenres = existingMovie.getGenres();
                }
                Movie updateMovie = new Movie();

                updateMovie.setId(idMovieUpd);
                updateMovie.setName(newTitle);
                updateMovie.setOfficialSiteUrl(newSiteUrl);
                updateMovie.setImageUrl(newImageUrl);
                updateMovie.setImage(newImage);
                updateMovie.setGenres(newGenres);

                mS.updateMovie(updateMovie);
                System.out.println("Movie updated successfully.");
            }
        } catch (DBException | MovieException e) {
            System.err.println("Error updating movie: " + e.getMessage());
        }
    }

    private static void deleteMovie() throws SQLException {
        System.out.println("Enter the ID movie to delete:");
        String idMovie = scann.nextLine();

        try {
            Movie existingMovie = mS.searchMovieById(idMovie);
            if (existingMovie != null) {
                System.out.println(
                        "Are you sure you want to delete the movie " + existingMovie.getName() + " ? (yes or no)");
                String confirmation = scann.nextLine();
                if (confirmation.equals("yes")) {
                    mS.deleteMovie(idMovie);
                    System.out.println("The movie " + existingMovie.getName() + " has been successfully deleted.");
                } else {
                    System.out.println("Deletion Canceled.");
                }
            } else {
                System.out.println("Movie with ID: " + idMovie + " was not found.");
            }
        } catch (DBException | MovieException e) {
            System.err.println("Error deleting movie: " + e.getMessage());
        }
    }
}
