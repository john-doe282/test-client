package com.andrew.client.model;

import com.andrew.rental.GetCarResponse;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class Car {
    private UUID id;

    private String model;
    private String type;
    private int pricePerHour;

    private Status status;

    @JsonBackReference
    private User owner;

//    @JsonManagedReference(value = "carReference")
    private List<ActiveRent> activeRents;

    public static Car fromGetCarResponse(GetCarResponse response) {
        return new CarBuilder().
                id(UUID.fromString(response.getId())).
                model(response.getModel()).
                type(response.getType()).
                pricePerHour(response.getPricePerHour()).
                status(Status.valueOf(response.getStatus().toString())).
                build();
    }
}
