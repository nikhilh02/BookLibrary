package com.assignment.usmobile.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "books")
public class Books {

    @Id
    private String id;

    private String title;
    private String author;
    private String isbn;
    private LocalDate publishedDate;
}
