package com.backend.movie_res_system.seed;
import com.backend.movie_res_system.entity.Showtime;
import com.backend.movie_res_system.entity.Seat;
import com.backend.movie_res_system.repository.SeatRepository;
import com.backend.movie_res_system.repository.ShowtimeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.List;

@Order(3)
@Component
public class SeatSeeder implements CommandLineRunner{
    private final SeatRepository seatRepository;
    private final ShowtimeRepository showtimeRepository;

    public SeatSeeder(SeatRepository seatRepository, ShowtimeRepository showtimeRepository) {
        this.seatRepository = seatRepository;
        this.showtimeRepository = showtimeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("▶ ShowtimeSeeder starting...");


        if (showtimeRepository.count() == 0) {
            throw new IllegalStateException("No showtimes found. Please seed showtimes first.");
        }
        if (seatRepository.count() > 0) {
            return;
        }
        List<Showtime> showtimes = showtimeRepository.findAll();
        System.out.println("Showtimes found: " + showtimes.size());

        for (Showtime showtime : showtimes) {
            for (String seatNumber : List.of("A1", "A2", "A3", "A4", "A5", "B1", "B2", "B3", "B4", "B5",
                    "C1", "C2", "C3", "C4", "C5", "D1", "D2", "D3", "D4", "D5")) {
                Seat seat = new Seat();
                seat.setSeatNumber(seatNumber);
                seat.setIsReserved(false);
                seat.setShowtime(showtime);
                seatRepository.save(seat);
            }
        }
        Thread.sleep(200);
    }

}
