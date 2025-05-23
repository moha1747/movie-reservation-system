package com.backend.movie_res_system.service;

import com.backend.movie_res_system.entity.*;
import com.backend.movie_res_system.repository.*;
import com.backend.movie_res_system.util.ReservationRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationSeatRepository reservationSeatRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;


    public ReservationService(ReservationSeatRepository reservationSeatRepository, ReservationRepository reservationRepository,
                              UserRepository userRepository, ShowtimeRepository showtimeRepository,  SeatRepository seatRepository) {
        this.reservationSeatRepository = reservationSeatRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
    }

    public List<ReservationSeat> getSeatsByID(Long sid) {
        return reservationSeatRepository.findBySeat_SeatId(sid);
    }

    public List<ReservationSeat> getSeatsByReservationID(Long rid) {
        return reservationSeatRepository.findByReservation_Rid(rid);
    }
    public Optional<Reservation> getReservationsForUser(Long userId) {
        return reservationRepository.findByUser_Uid(userId);
    }

    public Reservation CreateReservation(ReservationRequest request) {
        if (request.getUserId() == null) {
            throw new IllegalArgumentException("Missing user id");
        }
        if (request.getSeatIds() == null) {
            throw new IllegalArgumentException("Missing seat id");
        }
        if (request.getShowtimeId() == null) {
            throw new IllegalArgumentException("Missing showtime id");
        }
        if (request.getSeatIds().isEmpty()) {
            throw new IllegalArgumentException("Seat ids list cannot be empty");
        }
        Optional<User> userLookup = userRepository.findById(request.getUserId());
        if (userLookup.isEmpty()) {
            throw new IllegalArgumentException("User not found with id: " + request.getUserId());
        }
        User user = userLookup.get();

        Optional<Showtime> showTimeLookup = showtimeRepository.findByShowtimeId(request.getShowtimeId());
        if (showTimeLookup.isEmpty()) {
            throw new IllegalArgumentException("show time not found with id: " + request.getShowtimeId());
        }

        Showtime showTime = showTimeLookup.get();
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setShowtime(showTime);
        reservation.setTimestamp(LocalDateTime.now());

        reservationRepository.save(reservation);

        List<ReservationSeat> reservationSeats = new ArrayList<>();
        for (Long seatId : request.getSeatIds()) {
            if (seatId == null) {
                continue;
            } else {
                Optional<Seat> seatLookup = seatRepository.findById(seatId);
                if (seatLookup.isPresent()) {
                    Seat seat = seatLookup.get();
                    ReservationSeat reservationSeat = new ReservationSeat(reservation, seat);
                    reservationSeat.setReservation(reservation);
                    reservationSeats.add(reservationSeat);
                } else {
                    throw new IllegalArgumentException("Seat with ID " + seatId + " not found");
                }
            }
        }
        reservation.setReservationSeats(reservationSeats);
        reservationSeatRepository.saveAll(reservationSeats);
        return reservation;
    }

    public Reservation UpdateReservation(ReservationRequest.ReservationUpdateRequest request) {
        if (request.getReservationId() == null) {
            throw new IllegalArgumentException("Reservation not found");
        }

        Optional<Reservation> reservationLookup = reservationRepository.findById(request.getReservationId());
        if (reservationLookup.isEmpty()) {
            throw new IllegalArgumentException("Cannot find reservation");
        } else {
            Reservation reservation = reservationLookup.get();
            for (ReservationSeat rs : reservation.getReservationSeats()) {
                Seat oldSeat = rs.getSeat();
                oldSeat.setIsReserved(false);
                seatRepository.save(oldSeat);
            }
            reservationSeatRepository.deleteAll(reservation.getReservationSeats());
            List<ReservationSeat> newReservationSeat = new ArrayList<>();
            for (Long seatId : request.getNewSeatIds()) {

                Seat seat = seatRepository.findById(seatId)
                        .orElseThrow(() -> new IllegalArgumentException("Seat not found: " + seatId));
                if (seat.getIsReserved()) {
                    throw new IllegalStateException("Seat " + seatId + " is already reserved");
                }
                seat.setIsReserved(true);
                seatRepository.save(seat);
                ReservationSeat rs = new ReservationSeat(reservation, seat);
                newReservationSeat.add(rs);

            }
            reservation.setReservationSeats(newReservationSeat);
            reservationRepository.save(reservation);
            return reservation;
        }
    }
}
