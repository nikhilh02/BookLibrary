package com.assignment.usmobile.controller;

import com.assignment.usmobile.entity.Books;
import com.assignment.usmobile.entity.BooksDto;
import com.assignment.usmobile.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BooksController.class)
public class BooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private ModelMapper modelMapper;

    @Autowired
    ObjectMapper objectMapper;


    private final Books book = new Books(
            "684b62cb9c6f1e58c98ac488", "Mock_Title", "Mocked_Author", "0123456789111", LocalDate.now());

    private final BooksDto dto = new BooksDto(
            "684b62cb9c6f1e58c98ac488", "Mock_Title", "Mocked_Author", "0123456789111", LocalDate.now());

    private final BooksDto sampleDto = new BooksDto(
            null, "Mock_Title", "Mocked_Author", "0123456789111", LocalDate.of(2020, 1, 1));

    private final Books sampleBook = new Books(
            "684b62cb9c6f1e58c98ac488", "Mock_Title", "Mocked_Author", "0123456789111", LocalDate.of(2020, 1, 1));

    @Test
    void testGetAllBooks() throws Exception {

        when(bookService.getAllBooks(0, 5)).thenReturn(List.of(book));
        when(modelMapper.map(book, BooksDto.class)).thenReturn(dto);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Mock_Title"))
                .andExpect(jsonPath("$[0].author").value("Mocked_Author"));
    }

    @Test
    void testMultiGetAllBooks() throws Exception {

        when(bookService.getAllBooks(0, 5)).thenReturn(List.of(book, book));
        when(modelMapper.map(book, BooksDto.class)).thenReturn(dto);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }


    @Test
    void testGetBookByIdFound() throws Exception {

        when(bookService.getBookById("684b62cb9c6f1e58c98ac488")).thenReturn(Optional.of(book));
        when(modelMapper.map(book, BooksDto.class)).thenReturn(dto);

        mockMvc.perform(get("/books/684b62cb9c6f1e58c98ac488"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Mock_Title"));
    }

    @Test
    void testGetBookByIdNotFound() throws Exception {
        when(bookService.getBookById("684b62cb9c6f1e58c98ac488")).thenReturn(Optional.empty());

        mockMvc.perform(get("/books/684b62cb9c6f1e58c98ac488"))
                .andExpect(status().isNotFound());
    }


    @Test
    void testDeleteBookFound() throws Exception {
        when(bookService.deleteBook("684b62cb9c6f1e58c98ac488")).thenReturn(true);

        mockMvc.perform(delete("/books/684b62cb9c6f1e58c98ac488"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteBookNotFound() throws Exception {
        when(bookService.deleteBook("684b62cb9c6f1e58c98ac488")).thenReturn(false);

        mockMvc.perform(delete("/books/684b62cb9c6f1e58c98ac488"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddBook() throws Exception {
        when(modelMapper.map(any(BooksDto.class), eq(Books.class)))
                .thenReturn(sampleBook);
        when(bookService.addBook(any(Books.class)))
                .thenReturn(sampleBook);
        when(modelMapper.map(any(Books.class), eq(BooksDto.class)))
                .thenReturn(new BooksDto("684b62cb9c6f1e58c98ac488", "Mock_Title", "Mocked_Author", "0123456789111", LocalDate.of(2020, 1, 1)));

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Mock_Title"))
                .andExpect(jsonPath("$.isbn").value("0123456789111"));
    }

    @Test
    void testAddBookFailure() throws Exception {
        BooksDto invalidRequestDto = new BooksDto(
                null, "", "Mocked_Author", "0123", LocalDate.of(2020, 1, 1));

        when(modelMapper.map(any(BooksDto.class), eq(Books.class)))
                .thenReturn(sampleBook);
        when(bookService.addBook(any(Books.class)))
                .thenReturn(sampleBook);
        when(modelMapper.map(any(Books.class), eq(BooksDto.class)))
                .thenReturn(new BooksDto("684b62cb9c6f1e58c98ac488", "Mock_Title", "Mocked_Author", "0123456789111", LocalDate.of(2020, 1, 1)));

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateBookFound() throws Exception {
        when(modelMapper.map(any(BooksDto.class), eq(Books.class)))
                .thenReturn(sampleBook);
        when(bookService.updateBook(eq("684b62cb9c6f1e58c98ac488"), any(Books.class)))
                .thenReturn(Optional.of(sampleBook));
        when(modelMapper.map(any(Books.class), eq(BooksDto.class)))
                .thenReturn(new BooksDto("684b62cb9c6f1e58c98ac488", "Mock_Title", "Mocked_Author", "0123456789111", LocalDate.of(2020, 1, 1)));

        mockMvc.perform(put("/books/684b62cb9c6f1e58c98ac488")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Mock_Title"));
    }

    @Test
    void testUpdateBookNotFound() throws Exception {
        when(modelMapper.map(any(BooksDto.class), eq(Books.class)))
                .thenReturn(sampleBook);
        when(bookService.updateBook(eq("684b62cb9c6f1e58c98ac488"), any(Books.class)))
                .thenReturn(Optional.empty());
        when(modelMapper.map(any(Books.class), eq(BooksDto.class)))
                .thenReturn(new BooksDto("684b62cb9c6f1e58c98ac488", "Mock_Title", "Mocked_Author", "0123456789111", LocalDate.of(2020, 1, 1)));

        mockMvc.perform(put("/books/684b62cb9c6f1e58c98ac488")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleDto)))
                .andExpect(status().isNotFound());
    }

}
