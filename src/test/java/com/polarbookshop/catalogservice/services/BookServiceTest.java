package com.polarbookshop.catalogservice.services;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.exceptions.BookAlreadyExistsException;
import com.polarbookshop.catalogservice.exceptions.BookNotFoundException;
import com.polarbookshop.catalogservice.repositories.BookRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

  @Mock
  private BookRepository bookRepository;
  @InjectMocks
  private BookService bookService;

  @Test
  void test_addBookToCatalog_throws_BookAlreadyExistsException() {
    var isbn = "1234567890";
    var  book = Book.of(isbn, "Title", "Author", 9.90, "Polarsophia");
    when(bookRepository.existsByIsbn(isbn)).thenReturn(true);
    assertThatThrownBy(() -> bookService.addBookToCatalog(book))
        .isInstanceOf(BookAlreadyExistsException.class)
        .hasMessage(String.format("Book with isbn %s already exists", isbn));
  }

  @Test
  void test_viewBookDetails_throws_BookNotFoundException() {
    var isbn = "1234567890";
    var  book = Book.of(isbn, "Title", "Author", 9.90, "Polarsophia");
    when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.empty());
    assertThatThrownBy(() -> bookService.viewBookDetails(isbn))
        .isInstanceOf(BookNotFoundException.class)
        .hasMessage(String.format("Book with isbn %s not found", isbn));
  }



}