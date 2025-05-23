package com.backend.movie_res_system.repository;

import com.backend.movie_res_system.entity.ReservationSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, Long> {

    List<ReservationSeat> findBySeat_SeatId(Long seatId);

    List<ReservationSeat> findByReservation_Rid(Long reservationId);
}
