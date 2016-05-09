package filmr.services;

import filmr.domain.Showing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Service
public class ShowingService extends BaseServiceClass<Showing, Long> {
	
	
	private static final int ASSUMED_MAX_H_LENGTH_OF_ANY_MOVIE = 4;
	
	@PersistenceContext
	@Autowired
	private EntityManager entityManager;
	
	public List<Showing> getAllMatchingParams(
			LocalDateTime from_date, 
			LocalDateTime to_date,
			Integer minimum_available_tickets,
			Long only_for_movie_with_id,
			Long only_for_theater_with_id,
			Long only_for_cinema_with_id,
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
		query.setParameter("onlyForCinemaWithId", only_for_cinema_with_id);
		query.setMaxResults(limit != null ? limit : 50);
		List<Showing> matchingShowings = query.getResultList();
		
		System.out.println("ShowingService returning " + matchingShowings.size() + " showings, by named query Showing.filteredAndOrdered:");
		String queryBeingMade = query.unwrap(org.hibernate.Query.class).getQueryString();
		System.out.println(queryBeingMade + "\n");
		
		
		return matchingShowings;
	}

    //Presupposes no previous conflicts
    public boolean showingTimeIsValid(Showing showingToSave) {

        List<Showing> surroundingShowings = 
        		getSurroundingShowings(showingToSave, ASSUMED_MAX_H_LENGTH_OF_ANY_MOVIE);
        System.out.println("nr of showings surrounding proposed new showing: "+surroundingShowings.size());

        for(Showing existingShowing : surroundingShowings){
            if(!notConflictingTime(existingShowing, showingToSave)){
                //Time conflict found
                return false;
            }
        }

        return true;

    }

    //TODO: do we need to deal with cases where dates are neither before nor after (same date/time)
    private boolean notConflictingTime(Showing existingShowing, Showing showingToSave) {
        LocalDateTime otherShowingStartTime = existingShowing.getShowDateTime();
        LocalDateTime otherShowingEndTime = existingShowing.getShowingEndTime();

        if(otherShowingStartTime.isAfter(showingToSave.getShowingEndTime())){
            return true;
        }else if(otherShowingEndTime.isBefore(showingToSave.getShowDateTime())){
            return true;
        }else return false;
        // TODO: can this be simplified? to
        // return otherShowingStartTime.isAfter(showingToSave.getShowingEndTime() || otherShowingEndTime.isBefore(showingToSave.getShowDateTime()
        // too dense?
    }
    

    private List<Showing> getSurroundingShowings(Showing showing, int hourMarginBeforeAndAfter) {
    	
        LocalDateTime selectedDateTime = showing.getShowDateTime();
        LocalDateTime startOfDate = selectedDateTime.minusHours(hourMarginBeforeAndAfter);
        LocalDateTime endOfDate = selectedDateTime.plusHours(hourMarginBeforeAndAfter);
        
        Long theaterId = showing.getTheater().getId();
        List<Showing>potentialConflicts = getAllMatchingParams(startOfDate,endOfDate,null,null,theaterId,null,null,false);
        
        Collections.sort(potentialConflicts);
        return potentialConflicts;
    }

}
