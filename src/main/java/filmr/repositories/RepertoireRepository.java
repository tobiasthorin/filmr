package filmr.repositories;

import filmr.domain.Repertoire;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Marco on 2016-04-26.
 */
public interface RepertoireRepository extends JpaRepository<Repertoire, Long> {
}
