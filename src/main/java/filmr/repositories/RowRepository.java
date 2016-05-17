package filmr.repositories;

import filmr.domain.Row;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Marco on 2016-05-16.
 */
public interface RowRepository extends JpaRepository<Row, Long> {
}
