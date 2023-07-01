package com.polarbookshop.catalogservice.domain;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

public record Book(
    @Id
    Long id,
    @NotBlank(message = "The book ISBN must be defined.")
    @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "The ISBN format must be valid.")
    String isbn,
    @NotBlank(message = "The book title must be defined")
    String title,
    @NotEmpty(message = "The book author must be defined")
    String author,
    String publisher,
    @NotNull(message = "The book price must be defined")
    @Positive(message = "The book price must be greater than zero")
    Double price,
    @CreatedDate
    Instant createdDate,
    @LastModifiedDate
    Instant lastModifiedDate,
    @Version
    int version
) {

  public static Book of(
      String isbn, String title, String author, Double price, String publisher
  ) {
    return new Book(null, isbn, title, author, publisher, price, null, null,  0);
  }
}
