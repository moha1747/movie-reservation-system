package com.backend.movie_res_system.util;

import com.backend.movie_res_system.entity.Seat;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReservationRequest {
    private Long userId;
    private Long showtimeId;
    private List<Long> seatIds;

    @Getter
    @Setter
    public static class ReservationUpdateRequest {
        private Long reservationId;
        private List<Long> newSeatIds;
    }
}
