// src/main/java/com/backend/app/controller/ReservationController.java
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

import com.backend.app.entity.Reservation;
import com.backend.app.service.ReservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    // Obter todas as reservas com paginação
    @GetMapping
    public ResponseEntity<Page<Reservation>> getAllReservations(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        Page<Reservation> reservations = service.getAllReservations(page, size);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // Obter uma reserva específica por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = service.getReservationById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    // Criar uma nova reserva
    @PostMapping
    public ResponseEntity<Reservation> saveReservation(@Valid @RequestBody Reservation reservation) {
        Reservation createdReservation = service.saveReservation(reservation);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }

    // Atualizar uma reserva existente por ID
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @Valid @RequestBody Reservation updatedReservation) {
        Reservation reservation = service.updateReservation(id, updatedReservation);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    // Deletar uma reserva por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        service.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
