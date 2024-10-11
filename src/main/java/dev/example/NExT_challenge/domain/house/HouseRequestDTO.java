package dev.example.NExT_challenge.domain.house;

public record HouseRequestDTO(String zipcode, House.OwnerShipStatus ownership, String location) {
}
