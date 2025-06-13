package com.assignment.usmobile.configuration;

import com.assignment.usmobile.entity.Books;
import com.assignment.usmobile.repository.BooksRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadSampleData(BooksRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                List<Books> books = List.of(
                        new Books(null, "Seed Book1", "Seed Author1", "1234567890123", LocalDate.of(2020, 1, 1)),
                        new Books(null, "Seed Book2", "Seed Author2", "1234567890124", LocalDate.of(2021, 3, 2)),
                        new Books(null, "Seed Book3", "Seed Author3", "1234567890125", LocalDate.of(2022, 5, 3)),
                        new Books(null, "Seed Book4", "Seed Author4", "1234567890126", LocalDate.of(2023, 7, 4)),
                        new Books(null, "Seed Book5", "Seed Author5", "1234567890127", LocalDate.of(2024, 10, 5)),
                        new Books(null, "Seed Book6", "Seed Author6", "1234567890128", LocalDate.of(2025, 12, 6))
                );
                repository.saveAll(books);
            }
        };
    }
}
