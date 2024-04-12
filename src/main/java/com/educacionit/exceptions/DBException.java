package com.educacionit.exceptions;

public class DBException extends Exception {
    private Integer errorCode;

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

    public DBException(String message, Throwable cause, Integer errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public DBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
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

}
