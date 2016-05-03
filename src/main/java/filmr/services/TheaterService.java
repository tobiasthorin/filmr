package filmr.services;

import filmr.domain.Theater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class TheaterService extends BaseServiceClass<Theater, Long> {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Theater> readfilteredEntries(Long cinema_id) {

		Query query = entityManager.createNamedQuery("Theater.filterByCinema", Theater.class);
		query.setParameter("cinemaId", cinema_id);

		List<Theater> results = query.getResultList();

		return results;
	}
}
