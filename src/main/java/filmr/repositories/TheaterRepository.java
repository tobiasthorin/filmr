package filmr.repositories;

import filmr.domain.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Marco on 2016-04-25.
 */
public interface TheaterRepository extends JpaRepository<Theater, Long> {
}
