package com.educacionit.exceptions;

public class GenreException extends Exception {
    private Integer errorCode;
    public static final int ERROR_1 = 1; // Error: Get All Genres
    public static final int ERROR_2 = 2; // Error: Add Genre
    public static final int ERROR_3 = 3; // Error: Delete Genre
    public static final int ERROR_4 = 4; // Error: Get Genre By Id Movie

    public GenreException(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public GenreException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public GenreException(Throwable cause, Integer errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public GenreException(Integer error, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = error;
    }

    public GenreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
            Integer errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public Integer geterrorCode() {
        return errorCode;
    }

    public void seterrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        switch (errorCode) {
            case ERROR_1:
                return "An error ocurred while displaying the movie genres.";
            case ERROR_2:
                return "An error ocurred while adding a genre to a movie.";
            case ERROR_3:
                return "An error ocurred while deleting a genre from a movie.";
            case ERROR_4:
                return "An error ocurred while displaying the movie genres.";
            default:
                return super.getMessage();
        }
    }

}
