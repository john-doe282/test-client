package com.andrew.client.request_test.rabbit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActiveRentMqttDto {
    @JsonProperty("client_id")
    private UUID clientId;

    @JsonProperty("car_id")
    private UUID carId;

    private int duration;
}
