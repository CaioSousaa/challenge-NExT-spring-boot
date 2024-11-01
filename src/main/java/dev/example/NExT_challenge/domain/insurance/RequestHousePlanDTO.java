package dev.example.NExT_challenge.domain.insurance;

import java.util.UUID;

public record RequestHousePlanDTO(UUID client_id, Long house_id) {
}
