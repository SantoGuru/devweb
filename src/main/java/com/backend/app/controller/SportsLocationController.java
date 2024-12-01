// src/main/java/com/backend/app/controller/SportsLocationController.java
package com.backend.app.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.app.entity.SportsLocation;
import com.backend.app.service.SportsLocationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/locations")
public class SportsLocationController {

    private final SportsLocationService service;

    public SportsLocationController(SportsLocationService service) {
        this.service = service;
    }

    // Obter todos os locais esportivos com paginação
    @GetMapping
    public ResponseEntity<Page<SportsLocation>> getAllLocations(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        Page<SportsLocation> locations = service.getAllLocations(page, size);
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    // Obter um local esportivo por ID
    @GetMapping("/{id}")
    public ResponseEntity<SportsLocation> getLocationById(@PathVariable Long id) {
        SportsLocation location = service.getLocationById(id);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    // Criar um novo local esportivo
    @PostMapping
    public ResponseEntity<SportsLocation> saveLocation(@Valid @RequestBody SportsLocation location) {
        SportsLocation createdLocation = service.saveLocation(location);
        return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
    }

    // Atualizar um local esportivo por ID
    @PutMapping("/{id}")
    public ResponseEntity<SportsLocation> updateLocation(@PathVariable Long id, @Valid @RequestBody SportsLocation updatedLocation) {
        SportsLocation location = service.updateLocation(id, updatedLocation);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    // Deletar um local esportivo por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        service.deleteLocation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
