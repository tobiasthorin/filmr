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

        List<Showing> surroundingShowings = getSurroundingShowings(showingToSave);
        System.out.println("Showings surrounding: "+surroundingShowings.size());
        if(surroundingShowings.size()==0){
            System.out.println("No surrounding showing");
            return true;
        }
        for(Showing existingShowing : surroundingShowings){
            if(!notConflictingTime(existingShowing, showingToSave)){
                //Time conflict found
                return false;
            }
        }

        return true;







//        //Compare pairs of showings
//        for(int i= 1; i<surroundingShowings.size();i++){
//            LocalDateTime firstShowingEndTime = surroundingShowings.get(i-1).getShowingEndtime();
//            LocalDateTime followingShowingStartTime = surroundingShowings.get(i).getShowDateTime();
//
//            if(showingFitsBetweenShowings(firstShowingEndTime, followingShowingStartTime, showing)){
//                return true;
//            }
//
//        }
//        return false;

    }

    private boolean notConflictingTime(Showing existingShowing, Showing showingToSave) {
        LocalDateTime otherShowingStartTime = existingShowing.getShowDateTime();
        LocalDateTime otherShowingEndTime = existingShowing.getShowingEndtime();

        if(otherShowingStartTime.isAfter(showingToSave.getShowingEndtime())){
            return true;
        }else if(otherShowingEndTime.isBefore(showingToSave.getShowDateTime())){
            return true;
        }else return false;
    }

    private boolean showingFitsBetweenShowings(LocalDateTime firstShowingEndtime, LocalDateTime secondShowingstartTime, Showing showingToFit ){
        LocalDateTime showingToFitStartTime = showingToFit.getShowDateTime();
        LocalDateTime showingToFitEndTime = showingToFit.getShowingEndtime();
        return showingToFitStartTime.isAfter(firstShowingEndtime) && showingToFitEndTime.isBefore(secondShowingstartTime);
    }

    private List<Showing> getSurroundingShowings(Showing showing) {
        LocalDateTime selectedDateTime = showing.getShowDateTime();
        LocalDateTime startOfDate = selectedDateTime.minusHours(4);
        LocalDateTime endOfDate = selectedDateTime.plusHours(4);
        Long theaterId = showing.getTheater().getId();
        List<Showing>potentialConflicts = getAllMatchingParams(startOfDate,endOfDate,null,null,theaterId,null,null,false);
        Collections.sort(potentialConflicts);
        return potentialConflicts;
    }

}
