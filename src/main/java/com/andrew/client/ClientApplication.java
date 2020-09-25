package com.andrew.client;

import com.andrew.client.model.*;
import com.andrew.client.request_test.CarRequestsTest;
import com.andrew.client.request_test.RentRequestsTest;
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
        final UserRequestsTest userRequestsTest = new UserRequestsTest();
        final CarRequestsTest carRequestsTest = new CarRequestsTest();
        final RentRequestsTest rentRequestsTest = new RentRequestsTest();

        try {
            System.out.println("\n\nUser test");
            userRequestsTest.createUsers();
            User[] users = userRequestsTest.getUsers();
            if (users.length > 0) {
                System.out.println("User insertion successful");
            }
            User owner = users[0];
            User client = users[1];

            userRequestsTest.addBankAccount(owner.getId());
            userRequestsTest.addBankAccount(client.getId());

            List<BankAccount> bankAccountsTest = userRequestsTest.getUser(
                    owner.getId()).getBankAccounts();
            if (bankAccountsTest.size() > 0) {
                System.out.println("Bank account addition successful");
            }

            System.out.println("\n\nCar test");
            carRequestsTest.createCars(owner.getId());
            Car[] cars = carRequestsTest.getCars();
            if (cars.length > 0) {
                System.out.println("Cars insertion successful");
            }
            Car car = cars[0];

            System.out.println("\n\nRent test");
            rentRequestsTest.rent(client.getId(), car.getId());
            ActiveRent[] rents = rentRequestsTest.getRents(client.getId());
            if (rents.length > 0) {
                System.out.println("Renting successful");
            }

            System.out.println(rents[0].getId().toString());
            rentRequestsTest.closeRent(rents[0].getId());
            rents = rentRequestsTest.getRents(client.getId());
            if (rents.length == 0) {
                System.out.println("Rent closure successful");
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }



    }

}
