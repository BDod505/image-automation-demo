package com.example.demo.image.automation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Configuration
public class AuthenticationInterceptor implements ClientHttpRequestInterceptor {
    @Value("${clo-set.token}")
    private String token;

    @Value("${clo-set.email}")
    private String email;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("api-version", "2.0");
        headers.add("X-User-Email", email);
        headers.add("Content-Type", "application/json");
        return execution.execute(request, body);
    }
}
