package filmr.services;

import filmr.controllers.TheaterController;
import filmr.domain.Showing;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowingService extends BaseServiceClass<Showing, Long> {
	
	private static final int ASSUMED_MAX_H_LENGTH_OF_ANY_MOVIE = 4;
	
	@PersistenceContext
	@Autowired
	private EntityManager entityManager;
	private final static org.apache.log4j.Logger logger = Logger.getLogger(TheaterController.class);
	
	public List<Showing> getAllMatchingParams(
			LocalDateTime from_date, 
			LocalDateTime to_date,
			Long minimum_available_tickets,
			Long only_for_movie_with_id,
			Long only_for_theater_with_id,
			Long only_for_cinema_with_id,
			Integer limit, 
			Boolean show_disabled_showings) {
		
		logger.info("show disabled showings: " + show_disabled_showings);
		// named query, works with null values  - see Showing.java
		Query query = entityManager.createNamedQuery("Showing.filteredAndOrdered", Showing.class);
		query.setParameter("showDisabledShowings", show_disabled_showings);
		query.setParameter("fromDate", from_date);
		query.setParameter("toDate", to_date);
		//query.setParameter("minimum_available_tickets", minimum_available_tickets);
		query.setParameter("onlyForMovieWithId", only_for_movie_with_id);
		query.setParameter("onlyForTheaterWithId", only_for_theater_with_id);
		query.setParameter("onlyForCinemaWithId", only_for_cinema_with_id);
		query.setMaxResults(limit != null ? limit : 50);
		List<Showing> matchingShowings = query.getResultList();
		
		logger.info("ShowingService returning " + matchingShowings.size() + " showings, by named query Showing.filteredAndOrdered:");
		String queryBeingMade = query.unwrap(org.hibernate.Query.class).getQueryString();
		logger.debug(queryBeingMade + "\n");

        if (minimum_available_tickets != null)
            matchingShowings = showingsWithMinimumAvailableTickets(minimum_available_tickets, matchingShowings);

		return matchingShowings;
	}

    //Presupposes no previous conflicts
    public boolean showingTimeNotOccupied(Showing showingToSave) {

        List<Showing> surroundingShowings = 
        		getSurroundingShowings(showingToSave, ASSUMED_MAX_H_LENGTH_OF_ANY_MOVIE);
        logger.debug("nr of showings surrounding proposed new showing: "+surroundingShowings.size());

        for(Showing existingShowing : surroundingShowings){
            if(!notConflictingTime(existingShowing, showingToSave)){
                //Time conflict found
                return false;
            }
        }

        return true;

    }

    private boolean notConflictingTime(Showing existingShowing, Showing showingToSave) {
        LocalDateTime otherShowingStartTime = existingShowing.getShowDateTime();
        LocalDateTime otherShowingEndTime = existingShowing.getShowingEndTime();

         return otherShowingStartTime.isAfter(showingToSave.getShowingEndTime()) || otherShowingEndTime.isBefore(showingToSave.getShowDateTime());

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

    private List<Showing> showingsWithMinimumAvailableTickets(Long minimunAvailableTickets, List<Showing> showings) {
	    return showings.stream()
			    .filter(showing -> showing.getAvailableTickets() >= minimunAvailableTickets)
			    .collect(Collectors.toList());


    }
}
