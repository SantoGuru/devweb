// src/main/java/com/backend/app/service/SportsLocationService.java
package com.backend.app.service;

import com.backend.app.entity.SportsLocation;
import com.backend.app.repository.SportsLocationRepository;
import com.backend.app.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class SportsLocationService {
    private final SportsLocationRepository repository;

    public SportsLocationService(SportsLocationRepository repository) {
        this.repository = repository;
    }

    // Obter todos os locais com paginação
    public Page<SportsLocation> getAllLocations(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    // Obter um local por ID
    public SportsLocation getLocationById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Local esportivo com ID " + id + " não encontrado"));
    }

    // Salvar um novo local esportivo
    public SportsLocation saveLocation(SportsLocation location) {
        if (location.getName() == null || location.getName().isEmpty()) {
            throw new IllegalArgumentException("O nome do local esportivo é obrigatório");
        }
        return repository.save(location);
    }

    // Atualizar um local esportivo existente
    public SportsLocation updateLocation(Long id, SportsLocation updatedLocation) {
        SportsLocation existingLocation = getLocationById(id);

        if (updatedLocation.getName() != null && !updatedLocation.getName().isEmpty()) {
            existingLocation.setName(updatedLocation.getName());
        }

        if (updatedLocation.getDescription() != null) {
            existingLocation.setDescription(updatedLocation.getDescription());
        }

        return repository.save(existingLocation);
    }

    // Deletar um local esportivo por ID
    public void deleteLocation(Long id) {
        SportsLocation existingLocation = getLocationById(id);
        repository.delete(existingLocation);
    }
}
