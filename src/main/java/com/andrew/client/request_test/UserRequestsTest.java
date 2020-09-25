package com.andrew.client.request_test;

import com.andrew.client.model.Role;
import com.andrew.client.model.User;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class UserRequestsTest {
    private final String url = "http://localhost:8080/users";
    private final RestTemplate restTemplate;

    public UserRequestsTest() {
        restTemplate = new RestTemplateBuilder().build();
    }

    private String performPostRequest(String url, Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        return restTemplate.postForObject(url, entity, String.class);

    }
    public void createUsers() {
        Map<String, Object> owner = new HashMap<>();
        owner.put("name", "Vitya");
        owner.put("surname", "Syrov");
        owner.put("email", "qwert@mail.ru");
        owner.put("login", "acc282");
        owner.put("passwordHash", "svjsnvjsnk21e5e");
        owner.put("role", "OWNER");

        Map<String, Object> client = new HashMap<>();

        client.put("name", "Andrew");
        client.put("surname", "Molnar");
        client.put("email", "nasdeb@ukr.net");
        client.put("login", "kit973");
        client.put("passwordHash", "Qv34n2jsnk21e5e");
        client.put("role", "CLIENT");

        performPostRequest(url, owner);
        performPostRequest(url, client);

    }

    public User[] getUsers() {
        return restTemplate.getForObject(url, User[].class);
    }

    public User getUser(UUID id) {
        String requestUrl = url + "/" + id.toString();
        return restTemplate.getForObject(requestUrl, User.class);
    }

    public void addBankAccount(UUID id) {
        String requestUrl = url + "/bank";
        Map<String, Object> bankAccount = new HashMap<>();

        Map<String, Object> userShort = new HashMap<>();
        userShort.put("id", id.toString());

        bankAccount.put("user", userShort);

        Random rand = new Random();
        String iban = "350920950" + rand.nextInt(10);
        bankAccount.put("iban", iban);

        bankAccount.put("balance", 42);

        performPostRequest(requestUrl, bankAccount);
    }

    public void deleteUser(UUID id) {
        String requestUrl = url + "/" + id.toString();

        restTemplate.delete(requestUrl);
    }
}
