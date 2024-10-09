package dev.example.NExT_challenge.domain.vehicle;

import dev.example.NExT_challenge.domain.client.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "_vehicle")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "the brand field needs to be filled in")
    private String brand;

    @NotBlank(message = "the model field needs to be filled in")
    private String model;

    @NotNull
    @Min(value = 1940, message = "valid car year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
