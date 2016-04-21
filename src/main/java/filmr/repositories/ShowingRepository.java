package filmr.repositories;

import filmr.domain.Showing;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Marco on 2016-04-21.
 */
public interface ShowingRepository extends JpaRepository<Showing, Long> {
}
