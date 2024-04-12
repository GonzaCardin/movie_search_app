package com.educacionit.exceptions;

public class DBException extends Exception {
    private Integer errorCode;
    public static final int ERROR_1 = 1;    //Error: Open Connection
    public static final int ERROR_2 = 2;    //Error: Close Connection

    public DBException(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public DBException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DBException(Throwable cause, Integer errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public DBException(Integer error,String message, Throwable cause) {
        super(message, cause);
        this.errorCode = error;
    }

    public DBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
            Integer errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }
    
    @Override
    public String getMessage() {
        switch (errorCode) {
            case ERROR_1:
                return "An error occurred while connecting to the database.";
            case ERROR_2:
                return "An error ocurred while desconnecting from the database";
            default:
                return super.getMessage();
        }
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

}
