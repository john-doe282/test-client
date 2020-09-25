package com.andrew.client.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class ActiveRent {

    private UUID id;

    private int duration;

    @JsonBackReference(value = "carReference")
    private Car car;

    @JsonBackReference(value = "clientReference")
    private User client;

}