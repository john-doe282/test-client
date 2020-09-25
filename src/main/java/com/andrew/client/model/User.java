package com.andrew.client.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class User {
    private UUID id;

    private String name;
    private String surname;
    private String email;
    private String login;
    private String passwordHash;

    private LocalDateTime createdAt;

    private Role role;

    @JsonManagedReference
    private List<Car> cars;

    @JsonManagedReference(value = "userReference")
    private List<BankAccount> bankAccounts;

    @JsonManagedReference(value = "clientReference")
    private List<ActiveRent> activeRents;


}