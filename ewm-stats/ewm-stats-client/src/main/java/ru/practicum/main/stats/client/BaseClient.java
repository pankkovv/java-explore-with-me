package ru.practicum.main.stats.client;

import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.main.stats.dto.RequestDto;

import java.util.List;
import java.util.Map;

public class BaseClient {
    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected ResponseEntity<Object> get(String path, Map<String, Object> parameters) {
        return makeAndSendRequestGet(HttpMethod.GET, path, parameters);
    }

    protected void post(String path, RequestDto body) {
        makeAndSendRequestPost(HttpMethod.POST, path, body);
    }

    private void makeAndSendRequestPost(HttpMethod method, String path, RequestDto body) {
        HttpEntity<RequestDto> requestEntity = new HttpEntity<>(body, defaultHeaders());

        try {
            rest.exchange(path, method, requestEntity, Object.class);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException(e.getResponseBodyAsString());
        }
    }

    private <T> ResponseEntity<Object> makeAndSendRequestGet(HttpMethod method, String path, Map<String, Object> parameters) {
        HttpEntity<T> requestEntity = new HttpEntity<>(defaultHeaders());

        ResponseEntity<Object> statsServerResponse;
        try {
            if (parameters != null) {
                statsServerResponse = rest.exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                statsServerResponse = rest.exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareGatewayResponse(statsServerResponse);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}