package dev.example.NExT_challenge.domain.client;

public record ClientRequestDTO(String name, int age, double income, int dependents, Client.MaritalStatus maritalStatus) {
}
