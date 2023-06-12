package com.polarbookshop.catalogservice.exceptions.handlers;

import com.polarbookshop.catalogservice.exceptions.BookAlreadyExistsException;
import com.polarbookshop.catalogservice.exceptions.BookNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookControllerAdvice {

  @ExceptionHandler(BookNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String bookNotFoundException(BookNotFoundException ex) {
    return ex.getMessage();
  }

  @ExceptionHandler(BookAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String bookAlreadyExistsException(BookAlreadyExistsException ex) {
    return ex.getMessage();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
    var errors = new HashMap<String, String>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      var fieldName = ((FieldError) error).getField();
      var errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }
}
