package edu.vrg18.libereya.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

//import org.springframework.security.access.AccessDeniedException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ReturnErrorDescriptionToRequestor returnErrorDescriptionToRequestor = new ReturnErrorDescriptionToRequestor(status, "Malformed JSON request", ex);
        return new ResponseEntity(returnErrorDescriptionToRequestor, status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(new ReturnErrorDescriptionToRequestor(status, "No handler found", ex), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ReturnErrorDescriptionToRequestor returnErrorDescriptionToRequestor = new ReturnErrorDescriptionToRequestor(status, "Method arg not valid", ex);
        returnErrorDescriptionToRequestor.addValidationErrors(ex.getBindingResult().getFieldErrors());
        return new ResponseEntity<Object>(returnErrorDescriptionToRequestor, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        ReturnErrorDescriptionToRequestor returnErrorDescriptionToRequestor = new ReturnErrorDescriptionToRequestor(BAD_REQUEST);
        returnErrorDescriptionToRequestor.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        returnErrorDescriptionToRequestor.setDebugMessage(ex.getMessage());
        return new ResponseEntity<>(returnErrorDescriptionToRequestor, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ReturnErrorDescriptionToRequestor returnErrorDescriptionToRequestor = new ReturnErrorDescriptionToRequestor(HttpStatus.NOT_FOUND, "Entity does not exist", ex);
        return new ResponseEntity<>(returnErrorDescriptionToRequestor, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnlawfulDeletionException.class)
    protected ResponseEntity<Object> handleUnlawfulDeletionEx(UnlawfulDeletionException ex, WebRequest request) {
        ReturnErrorDescriptionToRequestor returnErrorDescriptionToRequestor = new ReturnErrorDescriptionToRequestor(HttpStatus.METHOD_NOT_ALLOWED, ex);
        return new ResponseEntity<>(returnErrorDescriptionToRequestor, HttpStatus.METHOD_NOT_ALLOWED);
    }

//    @ExceptionHandler(AccessDeniedException.class)
//    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
//        ReturnErrorDescriptionToRequestor returnErrorDescriptionToRequestor = new ReturnErrorDescriptionToRequestor(HttpStatus.FORBIDDEN, "You do not have permission!", ex);
//        return new ResponseEntity<>(returnErrorDescriptionToRequestor, HttpStatus.FORBIDDEN);
//    }

//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
//        ReturnErrorDescriptionToRequestor returnErrorDescriptionToRequestor = new ReturnErrorDescriptionToRequestor(HttpStatus.INTERNAL_SERVER_ERROR, "Other exception", ex);
//        return new ResponseEntity<>(returnErrorDescriptionToRequestor, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
