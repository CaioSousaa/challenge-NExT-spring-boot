package dev.example.NExT_challenge.repositories;

import dev.example.NExT_challenge.domain.insurance.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}
