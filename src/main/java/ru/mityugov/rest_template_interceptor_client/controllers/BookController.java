package ru.mityugov.rest_template_interceptor_client.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mityugov.rest_template_interceptor_client.models.Book;
import ru.mityugov.rest_template_interceptor_client.services.BookService;

import java.util.List;

@RestController
@RequestMapping("/book1")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping
    public List<Book> getAllBooks() {
        return service.getAllBook();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable("id") long id) {
        return service.getBookById(id);
    }
}
