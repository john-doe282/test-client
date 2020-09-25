package com.andrew.client;

import com.andrew.client.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ClientApplication.class, args);
        System.out.println("This is a test");
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.build();

        String url = "http://localhost:8080/users/dfb082ca-266d-4289-9e2a-146ebe4d2ccf";
        User user = null;
        try {
            user = restTemplate.getForObject(url, User.class);
        } catch (HttpStatusCodeException ex) {
            System.out.println(ex.getResponseBodyAsString());
        }
        System.out.println(user.getCars().get(0).getStatus());
    }

}
