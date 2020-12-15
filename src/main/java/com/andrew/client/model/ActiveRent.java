package com.andrew.client.model;

import com.andrew.rental.GetRentResponse;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActiveRent {

    private UUID id;

    private int duration;

//    @JsonBackReference(value = "carReference")
    @JsonIgnore
    private Car car;

//    @JsonBackReference(value = "clientReference")
    @JsonIgnore
    private User client;

    public static ActiveRent fromRentResponse (GetRentResponse response) {
        return new ActiveRentBuilder().
                id(UUID.fromString(response.getId())).
                duration(response.getDuration()).
                build();
    }

}