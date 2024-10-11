package dev.example.NExT_challenge.controller;

import dev.example.NExT_challenge.domain.vehicle.Vehicle;
import dev.example.NExT_challenge.domain.vehicle.VehicleRequestDTO;
import dev.example.NExT_challenge.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/vehicle")
@AllArgsConstructor
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/{client_id}")
    public ResponseEntity<Vehicle> create(@PathVariable UUID client_id, @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        return ResponseEntity.ok(this.vehicleService.createVehicle(vehicleRequestDTO, client_id));
    }
}
