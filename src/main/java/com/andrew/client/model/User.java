package com.andrew.client.model;

import com.andrew.rental.BankAccountResponse;
import com.andrew.rental.UserResponse;
import com.andrew.rental.UsersShort;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public static User fromUsersShort (UsersShort response) {
        return new UserBuilder().
                id(UUID.fromString(response.getId())).
                name(response.getName()).
                surname(response.getSurname()).
                email(response.getEmail()).
                login(response.getLogin()).
                role(Role.valueOf(response.getRole().toString())).
                build();
    }

    public static User fromUserResponse (UserResponse response) {
        List<BankAccountResponse> accountsList = response.
                getBankAccounts().getBankAccountsList();
        List<BankAccount> convertedAccounts = accountsList.stream().
                map(BankAccount::fromBankAccountResponse).
                collect(Collectors.toList());

        return new UserBuilder().
                id(UUID.fromString(response.getId())).
                name(response.getName()).
                surname(response.getSurname()).
                email(response.getEmail()).
                login(response.getLogin()).
                role(Role.valueOf(response.getRole().toString())).
                bankAccounts(convertedAccounts).
                build();
    }
}