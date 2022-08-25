package com.demo.SpringDBwithUI.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NoSuchElementException.class)
    protected ResponseEntity<Object> handleNoSuchElement(NoSuchElementException ex, WebRequest request) {
        ApiError bodyOfResponse = new ApiError(LocalDateTime.now().toString(), HttpStatus.NOT_FOUND, ex.toString(), "The requested product or company does not exist");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = DuplicateEntryException.class)
    protected ResponseEntity<Object> handleDuplicateEntry(DuplicateEntryException ex, WebRequest request) {
        ApiError bodyOfResponse = new ApiError(LocalDateTime.now().toString(), HttpStatus.CONFLICT, ex.toString(), "The requested object already exists in the database");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }


    @Override
    //@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError bodyOfResponse = new ApiError(LocalDateTime.now().toString(), HttpStatus.METHOD_NOT_ALLOWED, ex.toString(), "The requested HTTP method is not supported on this resource");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED, request);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        ApiError bodyOfResponse = new ApiError(LocalDateTime.now().toString(), HttpStatus.NOT_FOUND, ex.toString(), "The resource identifier is not of the expected type for the selected method");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    //@ExceptionHandler(value = MissingServletRequestParameterException.class)
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError bodyOfResponse = new ApiError(LocalDateTime.now().toString(), HttpStatus.BAD_REQUEST, ex.toString(), "Request parameters are not in the form expected by the API");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        ApiError bodyOfResponse = new ApiError(LocalDateTime.now().toString(), HttpStatus.BAD_REQUEST, ex.toString(), "The selected values for the parameters violate database constraints");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
