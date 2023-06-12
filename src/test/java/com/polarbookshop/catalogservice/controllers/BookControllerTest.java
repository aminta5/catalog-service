package com.polarbookshop.catalogservice.controllers;

import com.polarbookshop.catalogservice.exceptions.BookNotFoundException;
import com.polarbookshop.catalogservice.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookService bookService;

  @Test
  void test_getBookByIsbn_throws_BookNotFoundException() throws Exception{

    String isbn = "1221221222";

    given(bookService.viewBookDetails(isbn))
        .willThrow(BookNotFoundException.class);

    mockMvc.perform(get("/books/" + isbn))
        .andExpect(status().isNotFound());
  }


}