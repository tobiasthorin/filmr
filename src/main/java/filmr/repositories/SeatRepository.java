package filmr.repositories;

import filmr.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Marco on 2016-05-16.
 */
public interface SeatRepository extends JpaRepository<Seat, Long>{
}
