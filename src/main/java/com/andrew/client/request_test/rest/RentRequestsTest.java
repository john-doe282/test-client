package com.andrew.client.request_test.rest;

import com.andrew.client.model.ActiveRent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RentRequestsTest {
    private final String url = "http://127.0.0.1:62110/rents";
    private final RestTemplate restTemplate;

    public RentRequestsTest() {
        restTemplate = new RestTemplate();
    }

    private void performPostRequest(String url, Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        restTemplate.postForObject(url, entity, String.class);

    }

    public void rent(UUID clientId, UUID carId) {
        Map<String, Object> rent = new HashMap<>();
        rent.put("clientId", clientId.toString());
        rent.put("carId", carId.toString());
        rent.put("duration", 12);

        performPostRequest(url, rent);

    }

    public ActiveRent[] getRents(UUID clientId) {
        String requestUrl = url + "/user/" + clientId.toString();
        return restTemplate.getForObject(requestUrl, ActiveRent[].class);
    }

    public ActiveRent getRent(UUID id) {
        String requestUrl = url + "/" + id.toString();
        return restTemplate.getForObject(requestUrl, ActiveRent.class);
    }

    public void closeRent (UUID id) {
        String requestUrl = url + "/" + id.toString();

        restTemplate.delete(requestUrl);
    }
}
