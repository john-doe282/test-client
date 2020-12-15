package com.andrew.client.request_test.rabbit.dto;

import com.andrew.client.model.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMqttDto {
    private String model;
    private String type;
    private int pricePerHour;

    private Status status;

    @JsonProperty("owner_id")
    private UUID ownerId;
}
