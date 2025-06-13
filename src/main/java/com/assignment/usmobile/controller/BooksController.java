package com.assignment.usmobile.controller;

import com.assignment.usmobile.entity.Books;
import com.assignment.usmobile.entity.BooksDto;
import com.assignment.usmobile.exception.ErrorResponse;
import com.assignment.usmobile.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@Tag(name = "Books", description = "Book library management APIs")
public class BooksController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ModelMapper modelMapper;

    private Books dtoToEntity(BooksDto dto) {
        return modelMapper.map(dto, Books.class);
    }

    private BooksDto entityToDto(Books entity) {
        return modelMapper.map(entity, BooksDto.class);
    }

    @Operation(summary = "Get a list of all books")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successfully fetched all books",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BooksDto.class))))
    })
    @GetMapping
    public ResponseEntity<List<BooksDto>> getAllBooks(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "5") int size) {
        List<BooksDto> books = bookService.getAllBooks(page, size).stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


    @Operation(summary = "Get a single book by Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successfully fetched the mentioned book",
                    content = @Content(schema = @Schema(implementation = BooksDto.class))),
            @ApiResponse(
                    responseCode = "404", description = "Book Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@PathVariable String id) {
        Optional<Books> optionalBook = bookService.getBookById(id);
        if (optionalBook.isPresent()) {
            BooksDto dto = entityToDto(optionalBook.get());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            ErrorResponse errorBody = new ErrorResponse("BookID Not Found");
            return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Add a new book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successfully added the new book",
                    content = @Content(schema = @Schema(implementation = BooksDto.class))),
            @ApiResponse(
                    responseCode = "400", description = "Invalid Inputs")
    })
    @PostMapping
    public ResponseEntity<BooksDto> addBook(@Valid @RequestBody BooksDto booksDto) {
        Books saved = bookService.addBook(dtoToEntity(booksDto));
        return new ResponseEntity<>(entityToDto(saved), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing book By Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successfully Updated mentioned book",
                    content = @Content(schema = @Schema(implementation = BooksDto.class))),
            @ApiResponse(
                    responseCode = "404", description = "Book Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable String id,
                                               @Valid @RequestBody BooksDto booksDto) {
        Optional<Books> updated = bookService.updateBook(id, dtoToEntity(booksDto));
        if (updated.isPresent()) {
            return new ResponseEntity<>(entityToDto(updated.get()), HttpStatus.OK);
        } else {
            ErrorResponse errorBody = new ErrorResponse("BookID Not Found");
            return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Delete an existing book By Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successfully Deleted the mentioned book"),
            @ApiResponse(
                    responseCode = "404", description = "Book Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable String id) {
        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            ErrorResponse errorBody = new ErrorResponse("BookID Not Found");
            return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
        }
    }
}
