package ru.mityugov.rest_template_interceptor_client.configs.interseptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

@Slf4j
public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {

    private final HttpHeaders headers;

    public RestTemplateHeaderModifierInterceptor(HttpHeaders headers) {
        this.headers = headers;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().addAll(headers);
        logRequest(request, body);
        ResponseWrapper responseWrapper = new ResponseWrapper(execution.execute(request, body));
        logResponse(responseWrapper);
        return responseWrapper;
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        log.info(MessageFormat.format(
                "Response status code: {0} Response status text: {1} Response headers: {2} Response body: {3}",
                response.getStatusCode(),
                response.getStatusText(),
                response.getHeaders(),
                new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8)
        ));
    }

    private void logRequest(HttpRequest request, byte[] body) {
        log.info(MessageFormat.format(
                "Request method -> uri: {0} -> {1} Request body: {2}",
                request.getMethod(),
                request.getURI(),
                new String(body, StandardCharsets.UTF_8)
        ));
    }

    private String getRequestBody(InputStream body) throws IOException {
        if (body.available() != 0) {
            StringBuilder requestBody = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(body))) {
                String line;
                if (reader.ready()) {
                    while ((line = reader.readLine()) != null) {
                        requestBody.append(line);
                    }
                }
            }
            return requestBody.toString();
        }
        return "";
    }
}
