package dev.example.NExT_challenge.repositories;

import dev.example.NExT_challenge.domain.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}
