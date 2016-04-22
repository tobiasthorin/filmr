package filmr.repositories;

import filmr.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Marco on 2016-04-22.
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
