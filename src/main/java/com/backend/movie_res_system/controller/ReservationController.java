package com.backend.movie_res_system.controller;

import com.backend.movie_res_system.entity.Reservation;
import com.backend.movie_res_system.service.ReservationService;
import com.backend.movie_res_system.util.ReservationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest request) {
        Reservation result = reservationService.CreateReservation(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity<Reservation> updateReservation(@RequestBody ReservationRequest.ReservationUpdateRequest request) {
        Reservation result = reservationService.UpdateReservation(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Reservation>> getReservationsByUserId(@PathVariable Long id) {
        Optional<Reservation> result = reservationService.getReservationsForUser(id);
        return ResponseEntity.ok(result);
    }
}
