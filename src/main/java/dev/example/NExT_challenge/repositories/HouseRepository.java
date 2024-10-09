package dev.example.NExT_challenge.repositories;

import dev.example.NExT_challenge.domain.house.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {
}
