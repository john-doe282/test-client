package com.andrew.client;

import com.andrew.client.model.*;
import com.andrew.client.request_test.grpc.GrpcCarRequestsTest;
import com.andrew.client.request_test.grpc.GrpcRentRequestsTest;
import com.andrew.client.request_test.grpc.GrpcUserRequestsTest;
import com.andrew.client.request_test.rest.CarRequestsTest;
import com.andrew.client.request_test.rest.RentRequestsTest;
import com.andrew.client.request_test.rest.UserRequestsTest;
import com.andrew.rental.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.List;

@SpringBootApplication
public class ClientApplication {
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
    public static void main(String[] args) {
//        System.out.println("REST TEST: \n");
//        restTest();

        System.out.println("\nGRPC TEST: \n");
        grpcTest();



    }

}
