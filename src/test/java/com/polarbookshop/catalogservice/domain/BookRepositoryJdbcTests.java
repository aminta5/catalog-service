package com.polarbookshop.catalogservice.domain;

import com.polarbookshop.catalogservice.config.DataConfig;
import com.polarbookshop.catalogservice.repositories.BookRepository;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest // each test method runs in a transaction and rolls it back. This way the database remains clean
@Import(DataConfig.class)
@AutoConfigureTestDatabase(
    replace = AutoConfigureTestDatabase.Replace.NONE
)
@ActiveProfiles("integration")
public class BookRepositoryJdbcTests {

  @Autowired
  public BookRepository bookRepository;

  @Autowired
  private JdbcAggregateTemplate jdbcAggregateTemplate;


  @Test
  void findBookByIsbnWhenExisting() {
    var bookIsbn = "1234561237";
    var book = Book.of(bookIsbn, "Title", "Author", 12.90, "Polarsophia");
    jdbcAggregateTemplate.insert(book);
    Optional<Book> actualBook = bookRepository.findByIsbn(bookIsbn);

    assertThat(actualBook).isPresent();
    assertThat(actualBook.get().isbn()).isEqualTo(book.isbn());
  }

  @Test
  void findBookByIsbnWhenNotExisting() {

    Optional<Book> actualBook = bookRepository.findByIsbn("1234561238");
    assertThat(actualBook).isEmpty();
  }

  @Test
  void findAllBooks() {
    var book1 = Book.of("1234561235", "Title", "Author", 12.90, "Polarsophia");
    var book2 = Book.of("123456123", "Another Title", "Another Author", 12.90, "Polarsophia");

    jdbcAggregateTemplate.insert(book1);
    jdbcAggregateTemplate.insert(book2);

    Iterable<Book> actualBooks = bookRepository.findAll();
    assertThat(StreamSupport.stream(actualBooks.spliterator(), true)
        .filter(book -> book.isbn().equals(book1.isbn()) || book.isbn().equals(book2.isbn()))
        .collect(Collectors.toList())).hasSize(2);
  }

  @Test
  void existsByIsbnWhenExisting() {
    var bookIsbn = "1234561239";
    var book = Book.of(bookIsbn, "Title", "Author", 12.90, "Polarsophia");
    jdbcAggregateTemplate.insert(book);
    boolean existing = bookRepository.existsByIsbn(bookIsbn);
    assertThat(existing).isTrue();
  }

  @Test
  void existByIsbnWhenNotExisting() {
    boolean existing = bookRepository.existsByIsbn("1234561240");
    assertThat(existing).isFalse();
  }

  @Test
  void deleteByIsbn() {
    var bookIsbn = "1234561241";
    var bookToCreate = Book.of(bookIsbn, "Title", "Author", 12.90, "Polarsophia");
    var persistBook = jdbcAggregateTemplate.insert(bookToCreate);

    bookRepository.deleteByIsbn(bookIsbn);
    assertThat(jdbcAggregateTemplate.findById(persistBook.id(), Book.class)).isNull();
  }
}
