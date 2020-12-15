package com.andrew.client.request_test.rabbit.dto;

import com.andrew.client.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMqttDto {
    private String name;
    private String surname;
    private String email;
    private String login;
    private String passwordHash;

    private LocalDateTime createdAt;

    private Role role;
}
