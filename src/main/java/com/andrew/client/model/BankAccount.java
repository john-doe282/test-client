package com.andrew.client.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public final class BankAccount {
    private UUID id;

    private String iban;
    private int balance;

    @JsonBackReference(value = "userReference")
    private User user;
}