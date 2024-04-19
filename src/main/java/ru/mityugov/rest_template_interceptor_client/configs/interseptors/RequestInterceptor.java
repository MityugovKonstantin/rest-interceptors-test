package ru.mityugov.rest_template_interceptor_client.configs.interseptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        logRequest(request);
        logResponse(response);
        return true; // Возвращает true, чтобы продолжить обработку, или false, чтобы прервать её
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws IOException {
        logRequest(request);
        logResponse(response);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws IOException {
        logRequest(request);
        logResponse(response);
    }

    private void logResponse(HttpServletResponse response) {
        log.info(MessageFormat.format(
                "Response status: {0} Headers: {1}",
                response.getStatus(),
                getAllResponseHeaders(response)
        ));
    }

    private List<String> getAllResponseHeaders(HttpServletResponse response) {
        List<String> headersName = new ArrayList<>();
        Iterator<String> headerNames = response.getHeaderNames().iterator();
        while (headerNames.hasNext()) {
            String headerName = headerNames.next();
            headersName.add("Header name: " + headerName + " Header value: " + response.getHeader(headerName));
        }
        return headersName;
    }


    private void logRequest(HttpServletRequest request) throws IOException {
        log.info(MessageFormat.format(
                "AuthType: {0} > Request URI: {1} > Headers: {2} > Query string: {3} > Request body: {4}",
                request.getAuthType(),
                request.getRequestURI(),
                getAllRequestHeaders(request),
                request.getQueryString(),
                getRequestBody(request)
        ));
    }

    private List<String> getAllRequestHeaders(HttpServletRequest request) {
        List<String> headersName = new ArrayList<>();
        Iterator<String> headerNames = request.getHeaderNames().asIterator();
        while (headerNames.hasNext()) {
            String headerName = headerNames.next();
            headersName.add("Header name: " + headerName + " Header value: " + request.getHeader(headerName));
        }
        return headersName;
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String line;
            if (reader.ready()) {
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
            }
        }
        return requestBody.toString();
    }
}