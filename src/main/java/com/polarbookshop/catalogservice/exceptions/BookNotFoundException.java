package com.polarbookshop.catalogservice.exceptions;

public class BookNotFoundException extends RuntimeException {
  public BookNotFoundException(String isbn) {
    super(String.format("Book with isbn %s not found", isbn));
  }
}
