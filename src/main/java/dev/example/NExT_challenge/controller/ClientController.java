package dev.example.NExT_challenge.controller;

import dev.example.NExT_challenge.domain.client.Client;
import dev.example.NExT_challenge.domain.client.ClientRequestDTO;
import dev.example.NExT_challenge.service.ClientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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



}
