// src/main/java/com/backend/app/service/ReservationService.java
package com.backend.app.service;

import com.backend.app.entity.Reservation;
import com.backend.app.repository.ReservationRepository;
import com.backend.app.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    // Criar uma nova reserva
    public Reservation saveReservation(Reservation reservation) {
        // Validação de horário da reserva
        if (reservation.getStartTime().isAfter(reservation.getEndTime())) {
            throw new IllegalArgumentException("A hora de início não pode ser posterior à hora de fim.");
        }

        // Verificar se já existe uma reserva no mesmo local e horário
        List<Reservation> existingReservations = repository.findBySportsLocationIdAndStartTimeBetween(
                reservation.getSportsLocation().getId(), reservation.getStartTime(), reservation.getEndTime());

        if (!existingReservations.isEmpty()) {
            throw new IllegalStateException("Local já está reservado para esse horário.");
        }

        return repository.save(reservation);
    }

    // Obter todas as reservas com paginação
    public Page<Reservation> getAllReservations(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    // Obter uma reserva específica por ID
    public Reservation getReservationById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva com ID " + id + " não encontrada."));
    }

    // Atualizar uma reserva existente
    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        Reservation existingReservation = getReservationById(id);

        // Validar e atualizar os campos da reserva existente
        if (updatedReservation.getStartTime() != null && updatedReservation.getEndTime() != null) {
            if (updatedReservation.getStartTime().isAfter(updatedReservation.getEndTime())) {
                throw new IllegalArgumentException("A hora de início não pode ser posterior à hora de fim.");
            }

            // Verificar conflitos de horário ao atualizar
            List<Reservation> conflictingReservations = repository.findBySportsLocationIdAndStartTimeBetween(
                    existingReservation.getSportsLocation().getId(), 
                    updatedReservation.getStartTime(), 
                    updatedReservation.getEndTime());

            // Remover a própria reserva da verificação de conflito
            conflictingReservations.remove(existingReservation);

            if (!conflictingReservations.isEmpty()) {
                throw new IllegalStateException("Local já está reservado para esse horário.");
            }

            existingReservation.setStartTime(updatedReservation.getStartTime());
            existingReservation.setEndTime(updatedReservation.getEndTime());
        }

        if (updatedReservation.getSportsLocation() != null) {
            existingReservation.setSportsLocation(updatedReservation.getSportsLocation());
        }

        if (updatedReservation.getUser() != null) {
            existingReservation.setUser(updatedReservation.getUser());
        }

        return repository.save(existingReservation);
    }

    // Deletar uma reserva por ID
    public void deleteReservation(Long id) {
        Reservation existingReservation = getReservationById(id);
        repository.delete(existingReservation);
    }
}
