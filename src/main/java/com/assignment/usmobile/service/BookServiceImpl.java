package com.assignment.usmobile.service;

import com.assignment.usmobile.entity.Books;
import com.assignment.usmobile.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BooksRepository booksRepository;

    @Override
    public Optional<Books> getBookById(String bookId) {
        return booksRepository.findById(bookId);
    }

    @Override
    public List<Books> getAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return booksRepository.findAll(pageable).getContent();
    }

    @Override
    public Books addBook(Books book) {
        return booksRepository.save(book);
    }

    @Override
    public Optional<Books> updateBook(String bookId, Books book) {
        if (booksRepository.existsById(bookId)) {
            book.setId(bookId);
            return Optional.of(booksRepository.save(book));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteBook(String bookId) {
        if (booksRepository.existsById(bookId)) {
            booksRepository.deleteById(bookId);
            return true;
        }
        return false;
    }
}
