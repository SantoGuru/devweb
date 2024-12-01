// src/main/java/com/backend/app/repository/SportsLocationRepository.java
package com.backend.app.repository;

import com.backend.app.entity.SportsLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SportsLocationRepository extends JpaRepository<SportsLocation, Long> {

    // Buscar locais esportivos pelo nome (ou parte do nome) - case insensitive
    List<SportsLocation> findByNameContainingIgnoreCase(String name);

    // Buscar locais por descrição (ou parte da descrição) - case insensitive
    List<SportsLocation> findByDescriptionContainingIgnoreCase(String description);

    // Obter todos os locais com paginação e ordenação
    Page<SportsLocation> findAll(Pageable pageable);
}
