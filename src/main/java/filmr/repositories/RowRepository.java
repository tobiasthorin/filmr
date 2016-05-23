package filmr.repositories;

import filmr.domain.Row;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RowRepository extends JpaRepository<Row, Long> {
}
