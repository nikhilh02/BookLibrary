package com.assignment.usmobile.service;


import com.assignment.usmobile.entity.Books;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Books> getBookById(String bookId);
    List<Books> getAllBooks(int page, int size);
    Books addBook(Books book);
    Optional<Books> updateBook(String bookId, Books book);
    boolean deleteBook(String bookId);
}
