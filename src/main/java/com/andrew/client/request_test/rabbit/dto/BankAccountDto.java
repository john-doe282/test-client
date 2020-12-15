package com.andrew.client.request_test.rabbit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {
    private String iban;
    private int balance;

    @JsonProperty("user_id")
    private UUID userId;
}
