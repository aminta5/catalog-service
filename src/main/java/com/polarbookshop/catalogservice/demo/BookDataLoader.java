package com.polarbookshop.catalogservice.demo;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.repositories.BookRepository;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
//@Profile("testdata")
@ConditionalOnProperty(name = "polar.testdata.enabled", havingValue = "true")
public class BookDataLoader {

  private final BookRepository bookRepository;

  public BookDataLoader(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void loadBookTestData() {
    bookRepository.deleteAll();
    var book = Book.of("1234567891", "Northern Lights", "Lyra Silverstar", 9.90, "Polarsophia");
    var book1 = Book.of("1234567892", "Polar Journey", "Iorek Polarson", 12.90, "Polarsophia");
    bookRepository.saveAll(List.of(book, book1));
  }

}
