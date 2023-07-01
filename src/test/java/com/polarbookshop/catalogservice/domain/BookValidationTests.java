package com.polarbookshop.catalogservice.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

class BookValidationTests {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }

  @Test
  void test_allFieldsValid_validationSucceeds() {
    var book = Book.of("1234567890", "My Book Title", "Some Author", 9.90, "Polarsophia");
    var validationResult = validator.validate(book);
    assertThat(validationResult).isEmpty();
  }

  @Test
  void test_isbnNotDefined_validationFauls() {
    var book = Book.of("", "Title", "Author", 9.90, "Polarsophia");
    var validationResult = validator.validate(book);
    List<String> constraintValidationMessages = validationResult.stream().map(ConstraintViolation::getMessage).toList();
    assertThat(constraintValidationMessages)
        .hasSize(2)
        .contains("The book ISBN must be defined.")
        .contains("The ISBN format must be valid.");
  }

  @Test
  void test_isbnInvalidFormat_validationFails() {
    var book = Book.of("a1234567890", "My Book Title", "Some Author", 9.90, "Polarsophia");
    var validationResult = validator.validate(book);
    List<String> constraintValidationMessages = validationResult.stream().map(ConstraintViolation::getMessage).toList();
    assertThat(constraintValidationMessages)
        .hasSize(1)
        .contains("The ISBN format must be valid.");
  }

  @Test
  void test_authorIsNotDefined_validationFails(){
    var book = Book.of("1234567890", "My Book Title", "", 9.90, "Polarsophia");
    var validationResult = validator.validate(book);
    assertThat(validationResult).hasSize(1);
    assertThat(validationResult.iterator().next().getMessage()).isEqualTo("The book author must be defined");
  }

  @Test
  void test_titleIsNotDefined_validationFails(){
    var book = Book.of("1234567890", "", "Author", 9.90, "Polarsophia");
    var validationResult = validator.validate(book);
    assertThat(validationResult).hasSize(1);
    assertThat(validationResult.iterator().next().getMessage()).isEqualTo("The book title must be defined");
  }

  @Test
  void test_priceIsNotDefined_validationFails(){
    var book = Book.of("1234567890", "My Book Title", "Author", null, "Polarsophia");
    var validationResult = validator.validate(book);
    assertThat(validationResult).hasSize(1);
    assertThat(validationResult.iterator().next().getMessage()).isEqualTo("The book price must be defined");
  }

  @Test
  void test_priceIsZero_validationFails(){
    var book = Book.of("1234567890", "My Book Title", "Author", 0.0, "Polarsophia");
    var validationResult = validator.validate(book);
    assertThat(validationResult).hasSize(1);
    assertThat(validationResult.iterator().next().getMessage()).isEqualTo("The book price must be greater than zero");
  }

  @Test
  void test_priceIsNegative_validationFails(){
    var book = Book.of("1234567890", "My Book Title", "Author", -9.90, "Polarsophia");
    var validationResult = validator.validate(book);
    assertThat(validationResult).hasSize(1);
    assertThat(validationResult.iterator().next().getMessage()).isEqualTo("The book price must be greater than zero");
  }

}