package com.shopping.ecartbackend.exception;

import com.shopping.ecartbackend.common.ApiError;
import com.shopping.ecartbackend.common.ApiResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.objenesis.ObjenesisException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

//override all the default implementation of ResponseEntityExceptionHandler with our customized error messages
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class EcartExceptionHandler extends ResponseEntityExceptionHandler {

    public ResponseEntity<Object> generateErrorResponse(ApiError apiError){
        return new ResponseEntity<>(apiError, apiError.getHttpStatusObject());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public  ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException exception){
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setErrorMessage(exception.getMessage());
        return generateErrorResponse(apiError);
    }



}
