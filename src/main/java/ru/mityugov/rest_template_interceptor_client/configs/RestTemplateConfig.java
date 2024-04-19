package ru.mityugov.rest_template_interceptor_client.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.mityugov.rest_template_interceptor_client.configs.interseptors.RestTemplateHeaderModifierInterceptor;

import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    @Value("${api.baseurl}") // Загрузка базового URL из application.properties
    private String baseUrl;

    @Value("${api.token}") // Загрузка токена из application.properties
    private String token;

    @Value("${api.connectionTimeout}")
    private int connectionTimeout;

    @Bean
    public RestTemplate restTemplate() {
        // Создаем RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Настраиваем фабрику для HTTP-запросов, чтобы установить таймауты
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000); // Таймаут соединения в миллисекундах

        // Устанавливаем фабрику для RestTemplate
        restTemplate.setRequestFactory(requestFactory);

        // Устанавливаем базовый URL
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));

        // Добавляем заголовок авторизации с Bearer Token
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        // Устанавливаем заголовки в RestTemplate
        restTemplate.setInterceptors(Collections.singletonList(new RestTemplateHeaderModifierInterceptor(headers)));

        return restTemplate;
    }
}
