package com.andrew.client.request_test.rabbit;

import com.andrew.client.model.BankAccount;
import com.andrew.client.model.Role;
import com.andrew.client.model.Status;
import com.andrew.client.model.User;
import com.andrew.client.request_test.rabbit.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.annotation.PreDestroy;
import java.util.UUID;

public class RabbitRequestsTest {
    private final String address = "tcp://localhost:1883";
    private final IMqttClient publisher;
    private final ObjectMapper mapper = new ObjectMapper();

    public RabbitRequestsTest() throws MqttException {
        publisher = new MqttClient(address,
                UUID.randomUUID().toString(), new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10000);

        publisher.connect(options);
    }

    public void AddUsersTest() throws JsonProcessingException, MqttException, InterruptedException {
        UserMqttDto user1 = new UserMqttDto();
        user1.setName("Max");
        user1.setSurname("Krylov");
        user1.setEmail("ytre@yandex.ua");
        user1.setLogin("qana2");
        user1.setPasswordHash("evvvfq32");
        user1.setRole(Role.OWNER);

        UserMqttDto user2 = new UserMqttDto();
        user2.setName("Andrew");
        user2.setSurname("Siversjyi");
        user2.setEmail("dsq@yandex.ua");
        user2.setLogin("qana2");
        user2.setPasswordHash("evvvfq32");
        user2.setRole(Role.CLIENT);

        MyMqttMessage<UserMqttDto> request = new MyMqttMessage<>();
        request.setRequestType(MqttRequestType.ADD);
        request.setPayload(user1);

        byte[] payload = mapper.writeValueAsBytes(request);
        MqttMessage message = new MqttMessage(payload);
        message.setQos(2);
        message.setRetained(false);

        publisher.publish("r/user", message);

        request.setPayload(user2);
        message.setPayload(mapper.writeValueAsBytes(request));
        publisher.publish("r/user", message);
        Thread.sleep(1000);

    }

    public void AddCarTest(UUID ownerId) throws JsonProcessingException, MqttException, InterruptedException {
        CarMqttDto car = new CarMqttDto();
        car.setModel("Toyota");
        car.setOwnerId(ownerId);
        car.setPricePerHour(43);
        car.setStatus(Status.AVAILABLE);
        car.setType("SUV");

        MyMqttMessage<CarMqttDto> request = new MyMqttMessage<>();
        request.setPayload(car);
        request.setRequestType(MqttRequestType.ADD);


        byte[] payload = mapper.writeValueAsBytes(request);
        MqttMessage message = new MqttMessage(payload);
        message.setQos(2);
        message.setRetained(false);

        publisher.publish("r/car", message);
        Thread.sleep(1000);


    }

    public void AddRentTest(UUID clientId, UUID carId) throws JsonProcessingException, MqttException, InterruptedException {
        ActiveRentMqttDto rentRequest = new ActiveRentMqttDto();
        rentRequest.setCarId(carId);
        rentRequest.setClientId(clientId);
        rentRequest.setDuration(568);

        MyMqttMessage<ActiveRentMqttDto> message = new MyMqttMessage<>();
        message.setPayload(rentRequest);
        message.setRequestType(MqttRequestType.ADD);

        ObjectMapper mapper = new ObjectMapper();

        byte[] payload = mapper.writeValueAsBytes(message);
        MqttMessage mqttMessage = new MqttMessage(payload);
        mqttMessage.setRetained(false);
        mqttMessage.setQos(2);

        publisher.publish("r/rent", mqttMessage);
        publisher.disconnect();
        Thread.sleep(1000);

    }

    public void AddBankAccountTest(UUID id) throws JsonProcessingException, MqttException, InterruptedException {
        BankAccountDto bankAccountRequest = new BankAccountDto();
        bankAccountRequest.setBalance(100);
        bankAccountRequest.setIban("239791078411");
        bankAccountRequest.setUserId(id);

        MyMqttMessage<BankAccountDto> message = new MyMqttMessage<>();
        message.setRequestType(MqttRequestType.ADD);
        message.setPayload(bankAccountRequest);

        byte[] payload = mapper.writeValueAsBytes(message);
        MqttMessage mqttMessage = new MqttMessage(payload);
        mqttMessage.setQos(2);
        mqttMessage.setRetained(false);

        publisher.publish("r/bank", mqttMessage);
        Thread.sleep(1000);

    }

    public void close() {
        try {
            publisher.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
