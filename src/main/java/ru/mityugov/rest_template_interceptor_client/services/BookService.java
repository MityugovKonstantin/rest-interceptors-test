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
import ru.mityugov.rest_template_interceptor_client.exceptions.ServiceException;
import ru.mityugov.rest_template_interceptor_client.models.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final RestTemplate restTemplate;

    public List<Book> getAllBook() {
        final String url = "/book";
        HttpEntity entity = new HttpEntity("", new HttpHeaders());
        ResponseEntity<List<Book>> res = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});
        log.info("response = " + res.getBody());
        return res.getBody();
    }

    public Book getBookById(long id) {
        final String url = "/book/{id}";
        HttpEntity httpEntity = new HttpEntity("", new HttpHeaders());
        ResponseEntity<Book> res = restTemplate.getForEntity(url, Book.class, Map.of("id", 1));
        log.info("response = " + res.getBody());
        return res.getBody();
    }
}
