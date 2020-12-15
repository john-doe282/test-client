package com.andrew.client.request_test.rabbit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MyMqttMessage<T> {
    @NonNull
    @JsonProperty("message_type")
    private MqttRequestType requestType;

    @NonNull
    T payload;
}
