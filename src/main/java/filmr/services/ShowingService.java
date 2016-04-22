package filmr.services;

import filmr.domain.Showing;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ShowingService extends BaseServiceClass<Showing, Long> {
	
	@PersistenceContext
	@Autowired
	private EntityManager entityManager;
	
	public List<Showing> getAllMatchingParams(
			LocalDateTime from_date, 
			LocalDateTime to_date, 
			Integer mininum_available_tickets,
			Long only_for_movie_with_id) {
		
		
		
		String sqlQuery = buildQueryForParams(from_date, to_date, mininum_available_tickets, only_for_movie_with_id);
		
		Query query = entityManager.createNativeQuery(sqlQuery, Showing.class);
		
		List<Showing> matchingShowings = (List<Showing>) query.getResultList();
		
		
		return matchingShowings;
	}
	
	
	private String buildQueryForParams(
			LocalDateTime from_date, 
			LocalDateTime to_date, 
			Integer mininum_available_tickets,
			Long only_for_movie_with_id
			) {
		
		
		String query = 
				"SELECT * FROM showing WHERE " +
				getMovieCondition(only_for_movie_with_id) + 
				"";
		
		return query;
	}
	
	private String getMovieCondition(Long only_for_movie_with_id) {
		if(only_for_movie_with_id < 1) {
			return "true";
		}
		
		return "movie_id = " + only_for_movie_with_id;
	}

}
