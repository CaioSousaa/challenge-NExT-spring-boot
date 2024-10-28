package dev.example.NExT_challenge.service;

import dev.example.NExT_challenge.domain.client.Client;
import dev.example.NExT_challenge.domain.house.House;
import dev.example.NExT_challenge.domain.house.HouseRequestDTO;
import dev.example.NExT_challenge.domain.house.UpdateHouseRequestDTO;
import dev.example.NExT_challenge.repositories.ClientRepository;
import dev.example.NExT_challenge.repositories.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HouseService {
    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private ClientRepository clientRepository;

    public House createHouse(HouseRequestDTO houseRequestDTO, UUID client_id) {
        Client client = this.clientRepository.findById(client_id)
                .orElseThrow(() -> new RuntimeException("client does not exist"));

        House newHouse = new House();

        newHouse.setClient(client);
        newHouse.setLocation(houseRequestDTO.location());
        newHouse.setZipcode(houseRequestDTO.zipcode());
        newHouse.setOwnership(houseRequestDTO.ownership());

        houseRepository.save(newHouse);

        return newHouse;
    }

    public List<House> getAllHouses() {
        return houseRepository.findAll();
    }

    public House updateHouse(UpdateHouseRequestDTO updateHouseRequestDTO, Long house_id ) {
       House house = this.houseRepository.findById(house_id).orElseThrow();

       if(updateHouseRequestDTO.client_id() == null) {
           house.setClient(house.getClient());
       } else {
           Client client = this.clientRepository.findById(updateHouseRequestDTO.client_id()).orElseThrow();
           house.setClient(client);
       }

       if(updateHouseRequestDTO.ownerShipStatus() == null) {
           house.setOwnership(house.getOwnership());
       } else {
           house.setOwnership(updateHouseRequestDTO.ownerShipStatus());
       }

        this.houseRepository.save(house);

       return house;
    }
}
