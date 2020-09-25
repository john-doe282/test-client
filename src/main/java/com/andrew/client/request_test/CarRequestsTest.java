package com.andrew.client.request_test;

import com.andrew.client.model.Car;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class CarRequestsTest {
    private final String url = "http://localhost:8080/cars";
    private final RestTemplate restTemplate;

    public CarRequestsTest() {
        restTemplate = new RestTemplate();
    }

    private void performPostRequest(String url, Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        restTemplate.postForObject(url, entity, String.class);

    }
    public void createCars(UUID ownerId) {
        Map<String, Object> owner = new HashMap<>();
        owner.put("id", ownerId.toString());

        Map<String, Object> car1 = new HashMap<>();
        car1.put("model", "Toyota");
        car1.put("type", "SUV");
        car1.put("pricePerHour", 3);
        car1.put("status", "AVAILABLE");
        car1.put("owner", owner);


        Map<String, Object> car2 = new HashMap<>();
        car2.put("model", "Nissan");
        car2.put("type", "SUV");
        car2.put("pricePerHour", 1);
        car2.put("status", "AVAILABLE");
        car2.put("owner", owner);

        performPostRequest(url, car1);
        performPostRequest(url, car2);

    }

    public Car[] getCars() {
        return restTemplate.getForObject(url, Car[].class);
    }

    public Car getUser(UUID id) {
        String requestUrl = url + "/" + id.toString();
        return restTemplate.getForObject(requestUrl, Car.class);
    }

    public void deleteCar(UUID id) {
        String requestUrl = url + "/" + id.toString();

        restTemplate.delete(requestUrl);
    }
}
