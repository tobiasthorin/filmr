package filmr.repositories;

import filmr.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Marco on 2016-04-22.
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
