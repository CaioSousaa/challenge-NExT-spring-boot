package dev.example.NExT_challenge.domain.client;

import java.util.UUID;

public record UpdateClientRequestDTO(String name, int age, int dependents, double income, Client.MaritalStatus maritalStatus) {
}
