package dev.example.NExT_challenge.controller;

import dev.example.NExT_challenge.domain.house.House;
import dev.example.NExT_challenge.domain.house.HouseRequestDTO;
import dev.example.NExT_challenge.domain.house.UpdateHouseRequestDTO;
import dev.example.NExT_challenge.service.HouseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/house")
@AllArgsConstructor
public class HouseController {

    @Autowired
    private HouseService houseService;

    @PostMapping("/{client_id}")
    public ResponseEntity<House> create(@Valid @PathVariable UUID client_id, @RequestBody HouseRequestDTO data) {
        return ResponseEntity.ok(this.houseService.createHouse(data, client_id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<House>> getAll() {
        return ResponseEntity.ok(this.houseService.getAllHouses());
    }

    @PutMapping("/update_house/{house_id}")

    public ResponseEntity<House> update(@PathVariable Long house_id, @RequestBody UpdateHouseRequestDTO data) {
        return ResponseEntity.ok(this.houseService.updateHouse(data, house_id));
    }
}
