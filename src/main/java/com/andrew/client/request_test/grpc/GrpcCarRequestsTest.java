package com.andrew.client.request_test.grpc;

import com.andrew.client.model.Car;
import com.andrew.rental.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GrpcCarRequestsTest {
    private String url = "localhost";

    private final ManagedChannel channel;
    private final CarServiceGrpc.CarServiceBlockingStub stub;


    public GrpcCarRequestsTest() {
        channel = ManagedChannelBuilder.forAddress(url, 6565).
                usePlaintext().build();
        stub = CarServiceGrpc.newBlockingStub(channel);
    }

    public void createCars(UUID ownerId) {

        AddCarRequest request = AddCarRequest.newBuilder().
                setModel("Toyota").
                setType("SUV").
                setPricePerHour(4).
                setStatus(Status.valueOf("AVAILABLE")).
                setOwnerId(ownerId.toString()).
                build();

        AddCarRequest request1 = AddCarRequest.newBuilder().
                setModel("Nissan").
                setType("SUV").
                setPricePerHour(1).
                setStatus(Status.valueOf("AVAILABLE")).
                setOwnerId(ownerId.toString()).
                build();

        stub.add(request);
        stub.add(request1);

    }

    public List<Car> getCars() {
        List<GetCarResponse> carsList = stub.all(AllCarsRequest.
                newBuilder().build()).getCarsList();
        List<Car> convertedCars = carsList.stream().
                map(Car::fromGetCarResponse).
                collect(Collectors.toList());
        return convertedCars;
    }

    public Car getCar(UUID id) {
        GetCarResponse response = stub.get(GetCarRequest.newBuilder().setId(id.toString()).build());
        return Car.fromGetCarResponse(response);

    }

    public void deleteCar(UUID id) {
        stub.delete(DeleteCarRequest.newBuilder().
                setId(id.toString()).build());
    }

    @PreDestroy
    public void close() {
        channel.shutdown();
    }
}
