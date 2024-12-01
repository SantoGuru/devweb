// src/main/java/com/backend/app/repository/ReservationRepository.java
package com.backend.app.repository;

import com.backend.app.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Buscar reservas por localização esportiva e intervalo de horário
    List<Reservation> findBySportsLocationIdAndStartTimeBetween(Long sportsLocationId, LocalDateTime start, LocalDateTime end);

    // Buscar reservas por localização esportiva com paginação
    Page<Reservation> findBySportsLocationId(Long sportsLocationId, Pageable pageable);

    // Buscar todas as reservas de um usuário específico (usando ID do usuário)
    List<Reservation> findByUserId(Long userId);

    // Buscar todas as reservas de um usuário específico com paginação
    Page<Reservation> findByUserId(Long userId, Pageable pageable);

    // Buscar reservas que acontecem em uma data específica
    List<Reservation> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    // Buscar todas as reservas com paginação
    Page<Reservation> findAll(Pageable pageable);
}
