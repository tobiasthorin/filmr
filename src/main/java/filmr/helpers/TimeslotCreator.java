package filmr.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import filmr.domain.Movie;
import filmr.domain.Showing;
import filmr.domain.Theater;

/**
 * 
 * @author Erik
 *
 */

public class TimeslotCreator {
    
	/**
	 * Used to facilitate scheduling of showings. An admin can easily see what time periods are available, as those Showings (not real ones) will look different to the rest
	 * @param showings
	 * @param minimumTimeSlotLengthInMinutes
	 * @return
	 */
    public static List<Showing> createExtendedShowingsListWithEmptyTimeSlots(List<Showing> showings, Long minimumTimeSlotLengthInMinutes) {
    	// guard
    	if(showings.size() <= 1) return showings;
    		
    	List<Showing> extendedListOfShowings = new ArrayList<>();
    	Map<Theater, List<Showing>> showingsListsByTheater = getShowingsListGroupedByShowingTheater(showings);
    	
    	// get the modified list for each of the grouped lists, and add all the modified lists to the same extendedListOfShowings.
    	showingsListsByTheater.forEach((theaterKey, listOfTheaterSpecificShowings) -> 
    	{
    		List<Showing> extendedListForSpecificTheater = addEmptyTimeSlotShowings(listOfTheaterSpecificShowings, minimumTimeSlotLengthInMinutes);
    		extendedListOfShowings.addAll(extendedListForSpecificTheater);
    	});
    	
    	// sort by date, see Showing compareTo()
    	Collections.sort(extendedListOfShowings);
    	return extendedListOfShowings;
    }
    
    // return is only meaningful if the showings list contains only showings from the same theater.
    private static List<Showing> addEmptyTimeSlotShowings(List<Showing> showings, Long minimumTimeSlotLength) {
    	if(showings.size() <= 1) return showings;
		
    	List<Showing> emptyShowings = new ArrayList<>();
    	
    	
    	Collections.sort(showings);
    	
    	
    	for(int i = 1; i < showings.size(); i++) {
    		// get two showings to compare the time gap between them
    		Showing s1 = showings.get(i -1);
    		Showing s2 = showings.get(i);
    		Long actualTimeSlotInMinutes = lengthInMinutesBetweenShowingMovieEndtimeAndNextShowingStartTime(s1, s2);
    		
    		if(actualTimeSlotInMinutes > minimumTimeSlotLength) {
    			
    			// create an empty showing, spanning the whole gap
    			Showing emptyShowing = new Showing();
    			Date endOfS1movie = s1.getShowingEndtime();
    			emptyShowing.setShowDateTime(new Date(endOfS1movie.getTime()));
    			
    			// create empty movie, to make .getShowingEndtime return something in frontend
    			Movie m = new Movie();
    			m.setTitle("Available time slot - " + s1.getTheater().getName());
    			m.setDescription("Available time slot - " + s1.getTheater().getName());
    			m.setLengthInMinutes(actualTimeSlotInMinutes);
    			
    			emptyShowing.setMovie(m);
    			emptyShowings.add(emptyShowing);
    			
    		}

    	}
    	
    	showings.addAll(emptyShowings);
    	
    	return showings;
    }
    
    private static Long lengthInMinutesBetweenShowingMovieEndtimeAndNextShowingStartTime(Showing showingWithMovieEndTime, Showing showingWithStartTime){
    	
    	Long ONE_MINUTE_IN_MILLIS = new Long(60000);
    	
    	Long millisBetween = 
    			showingWithStartTime.getShowDateTime().getTime() // the one further into the future
    			- showingWithMovieEndTime.getShowingEndtime().getTime();
    	return Math.floorDiv(millisBetween, ONE_MINUTE_IN_MILLIS);
    }
    
    
    
    
    private static Map<Theater,List<Showing>> getShowingsListGroupedByShowingTheater(List<Showing> showings) {
    	
    	Map<Theater,List<Showing>> mapOfLists = 
    			showings.stream()
    			.collect(Collectors.groupingBy(showing -> showing.getTheater()));
    	
    	return mapOfLists;
    }
}
