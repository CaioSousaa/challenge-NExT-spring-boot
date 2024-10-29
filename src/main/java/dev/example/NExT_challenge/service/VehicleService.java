package dev.example.NExT_challenge.service;

import dev.example.NExT_challenge.domain.client.Client;
import dev.example.NExT_challenge.domain.vehicle.Vehicle;
import dev.example.NExT_challenge.domain.vehicle.VehicleRequestDTO;
import dev.example.NExT_challenge.repositories.ClientRepository;
import dev.example.NExT_challenge.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Vehicle createVehicle(VehicleRequestDTO data, UUID client_id) {
        Vehicle newVehicle = new Vehicle();

        Client client = this.clientRepository.findById(client_id)
                .orElseThrow(() -> new RuntimeException("non-existing customer"));

        newVehicle.setBrand(data.brand());
        newVehicle.setYear(data.year());
        newVehicle.setModel(data.model());
        newVehicle.setClient(client);

        vehicleRepository.save(newVehicle);

        return newVehicle;
    }

    public Vehicle deleteVehicle(Long vehicle_id) {
        Vehicle vehicle = this.vehicleRepository.findById(vehicle_id)
                .orElseThrow(() -> new RuntimeException("Vehicle does not exist"));

        this.vehicleRepository.delete(vehicle);

        return vehicle;
    }
}
