package ru.mityugov.rest_template_interceptor_client.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mityugov.rest_template_interceptor_client.models.Book;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final RestTemplate restTemplate;

    public List<Book> getAllBook() {
        final String url = "/book";
        HttpEntity<String> entity = new HttpEntity<>("", new HttpHeaders());
        ResponseEntity<List<Book>> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {}
        );
        log.info("Books (in BookService): " + response.getBody());
        return response.getBody();
    }

    public Book getBookById(long id) {
        final String url = "/book/{id}";
        ResponseEntity<Book> response = restTemplate.getForEntity(url, Book.class, Map.of("id", 1));
        log.info("Book (in BookService): " + response.getBody());
        return response.getBody();
    }
}
