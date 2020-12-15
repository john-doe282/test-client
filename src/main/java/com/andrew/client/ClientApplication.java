package com.andrew.client;

import com.andrew.client.model.*;
import com.andrew.client.model.Role;
import com.andrew.client.request_test.grpc.GrpcCarRequestsTest;
import com.andrew.client.request_test.grpc.GrpcRentRequestsTest;
import com.andrew.client.request_test.grpc.GrpcUserRequestsTest;
import com.andrew.client.request_test.rabbit.RabbitRequestsTest;
import com.andrew.client.request_test.rabbit.dto.ActiveRentMqttDto;
import com.andrew.client.request_test.rabbit.dto.MqttRequestType;
import com.andrew.client.request_test.rabbit.dto.MyMqttMessage;
import com.andrew.client.request_test.rest.CarRequestsTest;
import com.andrew.client.request_test.rest.RentRequestsTest;
import com.andrew.client.request_test.rest.UserRequestsTest;
import com.andrew.rental.AddRentRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class ClientApplication {
    private static final UserRequestsTest userRequest = new UserRequestsTest();
    private static final CarRequestsTest carRequest = new CarRequestsTest();
    private static final RentRequestsTest rentRequest = new RentRequestsTest();

    public static void restTest() {
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


    public static void grpcTest() {
//        try {
            final GrpcUserRequestsTest userRequestsTest = new GrpcUserRequestsTest();
            final GrpcCarRequestsTest carRequestsTest = new GrpcCarRequestsTest();
            final GrpcRentRequestsTest rentRequestsTest = new GrpcRentRequestsTest();

            System.out.println("\n\nUser test");
            userRequestsTest.createUsers();
            List<User> users = userRequestsTest.getUsers();
            if (users.size() > 0) {
                System.out.println("User insertion successful");
            }
            User owner = users.get(0);
            User client = users.get(1);

            userRequestsTest.addBankAccount(owner.getId());
            userRequestsTest.addBankAccount(client.getId());

            List<BankAccount> bankAccountsTest = userRequestsTest.getUser(
                    owner.getId()).getBankAccounts();
            if (bankAccountsTest.size() > 0) {
                System.out.println("Bank account addition successful");
            }

            System.out.println("\n\nCar test");
            carRequestsTest.createCars(owner.getId());
            List<Car> cars = carRequestsTest.getCars();
            if (cars.size() > 0) {
                System.out.println("Cars insertion successful");
            }
            Car car = cars.get(0);

            System.out.println("\n\nRent test");
            rentRequestsTest.rent(client.getId(), car.getId());
            List<ActiveRent> rents = rentRequestsTest.getRents(client.getId());
            if (rents.size() > 0) {
                System.out.println("Renting successful");
            }

            System.out.println(rents.get(0).getId().toString());
            rentRequestsTest.closeRent(rents.get(0).getId());
            rents = rentRequestsTest.getRents(client.getId());
            if (rents.size() == 0) {
                System.out.println("Rent closure successful");
            }


//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }


    }

    public static void rabbitTest() throws MqttException, JsonProcessingException, InterruptedException {
        final RabbitRequestsTest rabbitRequestsTest = new RabbitRequestsTest();

        System.out.println("\n\nUser test");
        rabbitRequestsTest.AddUsersTest();
        User[] users = userRequest.getUsers();

        if (users.length > 0) {
            System.out.println("User insertion successful");
        }
        User owner = users[0];
        User client = users[1];

        rabbitRequestsTest.AddBankAccountTest(owner.getId());

        rabbitRequestsTest.AddBankAccountTest(client.getId());

        List<BankAccount> bankAccountsTest = userRequest.getUser(
                owner.getId()).getBankAccounts();
        if (bankAccountsTest.size() > 0) {
            System.out.println("Bank account addition successful");
        }

        System.out.println("\n\nCar test");
        rabbitRequestsTest.AddCarTest(owner.getId());
        Car[] cars = carRequest.getCars();
        if (cars.length > 0) {
            System.out.println("Cars insertion successful");
        }
        Car car = cars[0];

        System.out.println("\n\nRent test");
        rabbitRequestsTest.AddRentTest(client.getId(), car.getId());
        ActiveRent[] rents = rentRequest.getRents(client.getId());
        if (rents.length > 0) {
            System.out.println("Renting successful");
        }
    }
    public static void main(String[] args) throws MqttException, JsonProcessingException, InterruptedException {
        rabbitTest();
//        grpcTest();
    }

}
