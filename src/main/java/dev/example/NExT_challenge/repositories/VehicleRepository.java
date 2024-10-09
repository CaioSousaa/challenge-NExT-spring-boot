package dev.example.NExT_challenge.repositories;

import dev.example.NExT_challenge.domain.vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
