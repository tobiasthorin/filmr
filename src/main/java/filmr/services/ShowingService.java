package filmr.services;

import filmr.domain.Showing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;


@Service
public class ShowingService extends BaseServiceClass<Showing, Long> {
	
	@PersistenceContext
	@Autowired
	private EntityManager entityManager;
	
	public List<Showing> getAllMatchingParams(
			Date from_date, 
			Date to_date, 
			Integer mininum_available_tickets,
			Long only_for_movie_with_id,
			Long only_for_theater_with_id,
			Integer limit, 
			Boolean show_disabled_showings) {
		
		System.out.println("show disabled showings: " + show_disabled_showings);
		// named query, works with null values  - see Showing.java
		Query query = entityManager.createNamedQuery("Showing.filteredAndOrdered", Showing.class);
		query.setParameter("showDisabledShowings", show_disabled_showings);
		query.setParameter("fromDate", from_date);
		query.setParameter("toDate", to_date);
		query.setParameter("onlyForMovieWithId", only_for_movie_with_id);
		query.setParameter("onlyForTheaterWithId", only_for_theater_with_id);
		query.setMaxResults(limit);
		
		List<Showing> matchingShowings = query.getResultList();
		
		System.out.println("ShowingService returning " + matchingShowings.size() + " showings, by named query Showing.filteredAndOrdered:");
		String queryBeingMade = query.unwrap(org.hibernate.Query.class).getQueryString();
		System.out.println(queryBeingMade + "\n");
		
		
		return matchingShowings;
	}


}
