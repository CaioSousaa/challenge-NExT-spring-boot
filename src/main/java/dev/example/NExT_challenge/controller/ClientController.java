package dev.example.NExT_challenge.controller;

import dev.example.NExT_challenge.domain.client.Client;
import dev.example.NExT_challenge.domain.client.ClientRequestDTO;
import dev.example.NExT_challenge.service.ClientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/api/client")
@AllArgsConstructor
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> create(@Valid @RequestBody ClientRequestDTO requestDTO) {
        Client newClint = this.clientService.createClient(requestDTO);

        return ResponseEntity.ok(newClint);

    }


    @GetMapping("/get-all")
    public ResponseEntity<List<Client>> getAll() {
        return ResponseEntity.ok(this.clientService.getAll());
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Client> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(this.clientService.findById(id));
    }
}
