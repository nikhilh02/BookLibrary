package com.assignment.usmobile.configuration;

import com.assignment.usmobile.entity.Books;
import com.assignment.usmobile.repository.BooksRepository;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Data
@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
