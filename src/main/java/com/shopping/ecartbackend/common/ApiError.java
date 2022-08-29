package com.shopping.ecartbackend.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiError {
    private HttpStatus httpStatusObject;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime currentTimestamp;
    private String errorMessage;
    private String debugMessage;

    private ApiError(){
        currentTimestamp = LocalDateTime.now();
    }
    public ApiError(HttpStatus httpStatus){
        this();
        this.httpStatusObject = httpStatus;
    }
    ApiError(HttpStatus httpStatus, Throwable exception){
        this();
        this.httpStatusObject = httpStatus;
        this.errorMessage = "Unexpected Error";
        this.debugMessage = exception.getLocalizedMessage();
    }

    ApiError(HttpStatus httpStatus, String message, Throwable exception){
        this();
        this.httpStatusObject = httpStatus;
        this.errorMessage = message;
        this.debugMessage = exception.getLocalizedMessage();
    }

    public HttpStatus getHttpStatusObject() {
        return httpStatusObject;
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }


}
