package com.andrew.client.model;

import com.andrew.rental.BankAccountResponse;
import com.andrew.rental.GetBankAccountResponse;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class BankAccount {
    private UUID id;

    private String iban;
    private int balance;

    @JsonBackReference(value = "userReference")
    private User user;

    public static BankAccount fromBankAccountResponse (BankAccountResponse response) {
        return new BankAccountBuilder().
                id(UUID.fromString(response.getId())).
                iban(response.getIban()).
                balance((int) response.getBalance()).
                build();
    }
}