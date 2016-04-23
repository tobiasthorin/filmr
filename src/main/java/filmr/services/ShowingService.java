package filmr.services;

import filmr.domain.Showing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
			Date from_date, 
			Date to_date, 
			Integer mininum_available_tickets,
			Long only_for_movie_with_id) {
		
		
		String sqlQueryString = buildQueryForParams(from_date, to_date, mininum_available_tickets, only_for_movie_with_id);	
		Query query = entityManager.createNativeQuery(sqlQueryString, Showing.class);
		
		List<Showing> matchingShowings = (List<Showing>) query.getResultList();
		System.out.println("\nShowingService returning " + matchingShowings.size() + " showings from query:\n" + sqlQueryString);
		
		return matchingShowings;
	}
	
	
	private String buildQueryForParams(
			Date from_date, 
			Date to_date, 
			Integer mininum_available_tickets,
			Long only_for_movie_with_id
			) {
		
		
		String query = 
				"SELECT * FROM showing " +
				"WHERE " +
				getFromDateCondition(from_date) + 
				" AND " +
				getMovieCondition(only_for_movie_with_id) + 
				" ORDER BY show_date_time ASC LIMIT 50";
		
		return query;
	}
	
	private String getMovieCondition(Long only_for_movie_with_id) {
		if(only_for_movie_with_id < 1) {
			return "(true)";
		}
		
		return "(movie_id = " + only_for_movie_with_id + ")";
	}
	
	private String getFromDateCondition(Date from_date) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		String fromDateCondition = "(show_date_time > '" + dateFormat.format(from_date) + "')";
		
		return fromDateCondition;
	}
	

}
