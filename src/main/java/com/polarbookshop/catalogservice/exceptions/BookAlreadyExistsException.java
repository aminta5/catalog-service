package com.polarbookshop.catalogservice.exceptions;

public class BookAlreadyExistsException extends RuntimeException {
  public BookAlreadyExistsException(String isbn) {
    super(String.format("Book with isbn %s already exists", isbn));
  }
}
