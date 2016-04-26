package filmr.repositories;

import filmr.domain.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Marco on 2016-04-26.
 */
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
