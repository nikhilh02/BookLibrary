package com.assignment.usmobile.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooksDto {

    private String id;

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Author must not be blank")
    @Size(min = 3, message = "Author must be at least 3 characters long")
    private String author;

    @NotBlank(message = "ISBN must not be blank")
    @Size(min = 13, max = 13, message = "ISBN must be exactly 13 characters long")
    private String isbn;

    @NotNull(message = "Published date is required")
    private LocalDate publishedDate;
}


