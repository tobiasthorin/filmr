package filmr.controllers;

import filmr.domain.Movie;
import filmr.domain.Showing;
import filmr.services.ShowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Marco on 2016-04-21.
 */
@RestController
@RequestMapping(value = "/api/showings")
public class ShowingController {

    @Autowired
    private ShowingService showingService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Showing> createShowing(@RequestBody Showing showing) {
        Showing savedShowing = showingService.saveEntity(showing);
        return new ResponseEntity<Showing>(savedShowing, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Showing> readShowing(@PathVariable Long id){
        Showing retrievedShowing = showingService.readEntity(id);
        return new ResponseEntity<Showing>(retrievedShowing, HttpStatus.OK);
    }

    
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Showing>> readAllShowings(
    		@RequestParam(name="from_date", required=false) @DateTimeFormat(iso = ISO.DATE) Date from_date, // @DateTimeFormat(iso = ISO.DATE) seems to work when we retrieve javascript Date objects
    		@RequestParam(name="to_date", required=false) @DateTimeFormat(iso = ISO.DATE) Date to_date,
    		@RequestParam(name="mininum_available_tickets", required=false) Integer mininum_available_tickets,
    		@RequestParam(name="only_for_movie_with_id", required=false, defaultValue = "-99") Long only_for_movie_with_id
    		) {
    	
    	List<Showing> retrievedShowings = new ArrayList<>();
    	if(only_for_movie_with_id == null && from_date == null) {
    		retrievedShowings = showingService.readAllEntities();    		
    	} else {
    		// default values that are hard to code as strings.. If not null -> use the value, else provide a default value
    		from_date = from_date != null ? from_date : new Date();
    		
    		retrievedShowings = showingService.getAllMatchingParams(from_date, to_date, mininum_available_tickets, only_for_movie_with_id);
    	}
    	
    	
    	HttpHeaders customHeaders = null;
    	ResponseEntity<List<Showing>> responseEntity = null;
		try {
			customHeaders = buildCustomHeadersForReadAll(retrievedShowings);
		} catch (JsonProcessingException e) {
			System.out.println("Couldn't parse movie list into JSON");
			e.printStackTrace();
		} finally {
			if(customHeaders == null) {
				responseEntity = new ResponseEntity<List<Showing>>(retrievedShowings, HttpStatus.PARTIAL_CONTENT);
			} else {				
				responseEntity = ResponseEntity.ok().headers(customHeaders).body(retrievedShowings);
			}
		}

    	return responseEntity;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Showing> updateShowing(@PathVariable Long id, @RequestBody Showing showing){
        if(showing.getId() == null){
            return new ResponseEntity<Showing>(new Showing(), HttpStatus.BAD_REQUEST);
        }

        Showing updatedShowing = showingService.saveEntity(showing);
        return new ResponseEntity<Showing>(updatedShowing, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteShowing(@PathVariable Long id) {
        showingService.deleteEntity(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    
    private HttpHeaders buildCustomHeadersForReadAll(List<Showing> showings) throws JsonProcessingException {
    	
    	HttpHeaders httpHeaders = new HttpHeaders();
    	httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
    	
    	List<Movie> distinctMovies = getDistinctMovieListForShowings(showings);

		httpHeaders.add("distinct_movies", new ObjectMapper().writeValueAsString(distinctMovies));
		httpHeaders.add("Status Code", "200 OK");

    	return httpHeaders;
    }
    
    private List<Movie> getDistinctMovieListForShowings(List<Showing> showings) {
    	
    	List<Movie> movies = 
    			showings.stream()
    			.parallel()
    			.map(showing -> showing.getMovie())
    			.distinct()
    			.collect(Collectors.toList());
    			
    	return movies;
    }

}
