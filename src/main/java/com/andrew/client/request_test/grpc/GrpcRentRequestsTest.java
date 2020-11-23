package com.andrew.client.request_test.grpc;

import com.andrew.client.model.ActiveRent;
import com.andrew.rental.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GrpcRentRequestsTest {
    private String url = "localhost";
    private final ManagedChannel channel;
    private final RentServiceGrpc.RentServiceBlockingStub stub;


    public GrpcRentRequestsTest() {
        channel = ManagedChannelBuilder.forAddress(url, 6565).
                usePlaintext().build();
        stub = RentServiceGrpc.newBlockingStub(channel);
    }
    public void rent(UUID clientId, UUID carId) {
        AddRentRequest request = AddRentRequest.newBuilder().
                setCarId(carId.toString()).
                setClientId(clientId.toString()).
                setDuration(13).
                build();
        stub.add(request);
    }

    public List<ActiveRent> getRents(UUID clientId) {
        List<GetRentResponse> rentsList = stub.allForUser(
                AllRentsForUserRequest.newBuilder().
                setClientId(clientId.toString()).
                build()).getRentsList();
        List<ActiveRent> convertedRents = rentsList.stream().
                map(ActiveRent::fromRentResponse).
                collect(Collectors.toList());

        return convertedRents;
    }

    public ActiveRent getRent(UUID id) {
        GetRentResponse response = stub.get(GetRentRequest.
                newBuilder().setRentId(id.toString()).build());

        return ActiveRent.fromRentResponse(response);
    }

    public void closeRent (UUID id) {
        stub.close(DeleteRentRequest.newBuilder().setId(id.toString()).build());
    }

    @PreDestroy
    public void close() {
        channel.shutdown();
    }
}
