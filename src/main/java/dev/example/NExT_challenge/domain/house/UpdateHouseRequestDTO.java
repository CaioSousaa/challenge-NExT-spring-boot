package dev.example.NExT_challenge.domain.house;

import java.util.UUID;

public record UpdateHouseRequestDTO(House.OwnerShipStatus ownerShipStatus, UUID client_id) {
}
