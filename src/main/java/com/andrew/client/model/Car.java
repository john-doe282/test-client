package com.andrew.client.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public final class Car {
    private UUID id;

    private String model;
    private String type;
    private int pricePerHour;

    private Status status;

    @JsonBackReference
    private User owner;

    @JsonManagedReference(value = "carReference")
    private List<ActiveRent> activeRents;

}
