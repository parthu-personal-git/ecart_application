package com.shopping.ecartbackend.exception;

import com.shopping.ecartbackend.common.ApiError;
import com.shopping.ecartbackend.common.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.objenesis.ObjenesisException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

//override all the default implementation of ResponseEntityExceptionHandler with our customized error messages
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class EcartExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(EcartExceptionHandler.class);

    public ResponseEntity<Object> generateErrorResponse(ApiError apiError){
        return new ResponseEntity<>(apiError, apiError.getHttpStatusObject());
    }


    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException exception){
        logger.error("element is not present in DB");
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND,"no element present in DB",exception);
        apiError.setErrorMessage(exception.getLocalizedMessage());
        return generateErrorResponse(apiError);
    }

    @ExceptionHandler(value = {EmptyInputException.class})
    public ResponseEntity<Object> handleEmptyInputException(EmptyInputException exception){
        logger.error("input fields are missing");
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,"input fields are missing");
        apiError.setErrorMessage(exception.getErrorMessage());
        return generateErrorResponse(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Method not supported here");
        ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, "method not allowed");
        apiError.setErrorMessage(ex.getMessage());
        return generateErrorResponse(apiError);
        //return super.handleHttpRequestMethodNotSupported(ex, headers, status, request);
    }



    //    //specific exceptions
//    @ExceptionHandler(value = { EntityNotFoundException.class })
//    public  ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException exception){
//        logger.error("Entity doesn't exist with given id ");
//        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
//        apiError.setErrorMessage(exception.getMessage());
//        return generateErrorResponse(apiError);
//
//    }
//
//    @ExceptionHandler(value = {NullPointerException.class})
//    public ResponseEntity<Object> handleNullPointerExcepton(NullPointerException exception){
//        logger.error("Null pointer exception : ");
//        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
//        apiError.setErrorMessage(exception.getMessage());
//        return generateErrorResponse(apiError);
//    }
//
//
//    //least preference : for generic exceptions
//
//    @ExceptionHandler(value = {Exception.class })
//    public ResponseEntity<Object> handleInvalidInputException(Exception exception){
//        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
//        apiError.setErrorMessage(exception.getMessage());
////        apiError.setDebugMessage(exception.getLocalizedMessage());
//        return generateErrorResponse(apiError);
//    }

}
