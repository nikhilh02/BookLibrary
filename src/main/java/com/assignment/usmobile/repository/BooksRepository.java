package com.assignment.usmobile.repository;

import com.assignment.usmobile.entity.Books;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends MongoRepository<Books, String> {
}
