package com.example.demo.image.automation.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class AuthenticationInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.add("Authorization","Bearer d06e24f611a345dbb17eeb6b0444fd14");
        headers.add("api-version","2.0");
        headers.add("X-User-Email","dpc.uattest7@marks-and-spencer.com");
        headers.add("Content-Type","application/json");
        return execution.execute(request,body);
    }
}
