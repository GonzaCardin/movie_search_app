package com.educacionit.exceptions;

public class MovieException extends Exception {
    private Integer errorCode;
    public static final int ERROR_1 = 1; // Error: Search By Id
    public static final int ERROR_2 = 2; // Error: Search All
    public static final int ERROR_3 = 3; // Error: Save
    public static final int ERROR_4 = 4; // Error: Update
    public static final int ERROR_5 = 5; // Error: Delete

    public MovieException(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public MovieException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public MovieException(Throwable cause, Integer errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public MovieException(Integer error, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = error;
    }

    public MovieException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
            Integer errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        switch (errorCode) {
            case ERROR_1:
                return "An error ocurred while searching for the movie by its ID ";
            case ERROR_2:
                return "An error ocurred while searching for the movies";
            case ERROR_3:
                return "An error ocurred while adding a new movie";
            case ERROR_4:
                return "An error ocurred while modifying a movie";
            case ERROR_5:
                return "An error ocurred while deleting a movie";
            default:
                return super.getMessage();
        }
    }

}
