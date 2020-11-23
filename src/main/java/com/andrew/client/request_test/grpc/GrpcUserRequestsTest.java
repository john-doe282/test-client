package com.andrew.client.request_test.grpc;

import com.andrew.client.model.User;
import com.andrew.rental.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import javax.annotation.PreDestroy;
import java.util.*;
import java.util.stream.Collectors;

public class GrpcUserRequestsTest {
    private String Url = "localhost";

    private final ManagedChannel channel;
    private final UserServiceGrpc.UserServiceBlockingStub stub;

    public GrpcUserRequestsTest() {
        channel = ManagedChannelBuilder.forAddress(Url, 6565).usePlaintext().build();
        stub = UserServiceGrpc.newBlockingStub(channel);
    }

    public void createUsers() {
        Map<String, Object> owner = new HashMap<>();
        AddUserRequest request = AddUserRequest.newBuilder().
                setName("Max").
                setSurname("Krylov").
                setEmail("ytre@yandex.ua").
                setLogin("qana2").
                setPasswordHash("evvvfq32").
                setRole(Role.valueOf("OWNER")).
                build();

        AddUserRequest request2 = AddUserRequest.newBuilder().
                setName("Sasha").
                setSurname("Seriy").
                setEmail("slvnks@yandex.ua").
                setLogin("qana2").
                setPasswordHash("evvvfq32").
                setRole(Role.valueOf("CLIENT")).
                build();
        stub.add(request);
        stub.add(request2);

    }

    public List<User> getUsers() {
        AllResponse response = stub.all(AllRequest.newBuilder().build());
        List<UsersShort> usersList = response.getUsersList();
        return usersList.stream().
                map(User::fromUsersShort).
                collect(Collectors.toList());
    }

    public User getUser(UUID id) {
        UserResponse response = stub.get(GetRequest.
                newBuilder().setId(id.toString()).build());
        return User.fromUserResponse(response);
    }

    public void addBankAccount(UUID id) {
        Random rand = new Random();
        AddBankAccountRequest request = AddBankAccountRequest.newBuilder().
                setIban("350920950" + rand.nextInt(10)).
                setBalance(42).
                setUserId(id.toString()).
                build();
        stub.addBankAccount(request);
    }

    public void deleteUser(UUID id) {
        stub.delete(DeleteRequest.newBuilder().setId(id.toString()).build());
    }

    @PreDestroy
    public void close() {
        channel.shutdown();
    }

}
