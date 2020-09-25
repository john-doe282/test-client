package com.andrew.client;

import com.andrew.client.model.Role;
import com.andrew.client.model.User;
import com.andrew.client.request_test.CarRequestsTest;
import com.andrew.client.request_test.UserRequestsTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ClientApplication.class, args);
        final UserRequestsTest userRequestsTest = new UserRequestsTest();

        userRequestsTest.createUsers();
        User[] users = userRequestsTest.getUsers();
        User owner = users[0];
        User client = users[1];

        userRequestsTest.addBankAccount(owner.getId());
        userRequestsTest.addBankAccount(client.getId());

        userRequestsTest.deleteUser(client.getId());

    }

}
