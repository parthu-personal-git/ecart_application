package com.shopping.ecartbackend.exception;

import org.springframework.http.HttpStatus;

public class EmptyInputException extends RuntimeException{
    private String errorMessage;
    private HttpStatus  httpStatus;
    public EmptyInputException(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
