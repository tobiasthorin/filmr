package filmr.repositories;

import filmr.domain.Repertoire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepertoireRepository extends JpaRepository<Repertoire, Long> {
}
