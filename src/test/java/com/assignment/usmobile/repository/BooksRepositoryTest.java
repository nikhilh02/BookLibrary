package com.assignment.usmobile.repository;

import com.assignment.usmobile.entity.Books;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class BooksRepositoryTest {

    @Autowired
    private BooksRepository booksRepository;

    private Books book;

    @BeforeEach
    void setUp() {
        booksRepository.deleteAll(); // Clean slate
        book = new Books(null, "Outliers", "Malcolm G", "1234567890123", LocalDate.of(2000, 6, 3));
        booksRepository.save(book);
    }

    @Test
    void testRepositoryFindBookById() {
        Optional<Books> foundBook = booksRepository.findById(book.getId());

        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("Outliers");
    }

    @Test
    void testRepositoryReturnAllBooks() {
        assertThat(booksRepository.findAll()).hasSize(1);
    }

    @Test
    void testRepositoryDeleteBookById() {
        booksRepository.deleteById(book.getId());
        assertThat(booksRepository.findById(book.getId())).isEmpty();
    }

    @Test
    void testRepositoryUpdateBook() {
        book.setTitle("Mind Games");
        booksRepository.save(book);

        Optional<Books> updated = booksRepository.findById(book.getId());
        assertThat(updated).isPresent();
        assertThat(updated.get().getTitle()).isEqualTo("Mind Games");
    }
}
