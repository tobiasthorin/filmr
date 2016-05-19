package filmr.controllers;

import filmr.domain.Movie;
import filmr.helpers.exceptions.FilmrBaseException;
import filmr.helpers.exceptions.FilmrExceptionModel;
import filmr.helpers.exceptions.FilmrPOSTRequestWithPredefinedIdException;
import filmr.helpers.exceptions.FilmrPUTRequestWithMissingEntityIdException;
import filmr.services.MovieService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/movies")
public class MovieController {
	
	private final static org.apache.log4j.Logger logger = Logger.getLogger(ShowingController.class);

    @Autowired
    private MovieService movieService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) throws FilmrPOSTRequestWithPredefinedIdException {
        if (movie.getId() != null) {
        	logger.warn("Trying to create movie with pre-set id.");
            throw new FilmrPOSTRequestWithPredefinedIdException("Trying to create movie with pre-set id.");
        }
        Movie savedMovie = movieService.saveEntity(movie);
        return new ResponseEntity<Movie>(savedMovie, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Movie> readMovie(@PathVariable Long id){
        Movie retrievedMovie = movieService.readEntity(id);
        return new ResponseEntity<Movie>(retrievedMovie, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Movie>> readAllMovies(
        @RequestParam(name="not_in_repertoire_with_id", required=false) Long not_in_repertoire_with_id
    ) {
        if(not_in_repertoire_with_id==null) {
            List<Movie> retrievedMovies = movieService.readAllEntities();
            return new ResponseEntity<List<Movie>>(retrievedMovies, HttpStatus.OK);
        }
        
        logger.info("Getting movies not already in repertoire with id " + not_in_repertoire_with_id );
        List<Movie> retrievedMovies = movieService.getAllMoviesNotInRepertoire(not_in_repertoire_with_id);
        return new ResponseEntity<List<Movie>>(retrievedMovies, HttpStatus.OK);

    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) throws FilmrPUTRequestWithMissingEntityIdException {
        if(movie.getId() == null){
        	logger.warn("Trying to update movie, but no movie has no id.");
            throw new FilmrPUTRequestWithMissingEntityIdException("Trying to update movie, but movie has no id.");
        }

        Movie updatedMovie = movieService.saveEntity(movie);
        return new ResponseEntity<Movie>(updatedMovie, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMovie(@PathVariable Long id) {
    	logger.info("deleting movie with id " + id);
        movieService.deleteEntity(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    // all custom errors should inherit from FilmrBaseException, so this should work for all of them. 
    @ExceptionHandler(FilmrBaseException.class)
    @ResponseBody
    public FilmrExceptionModel handleBadRequest(HttpServletRequest req, FilmrBaseException ex) {
    	logger.debug("Catching custom error in controller.. ");
        return new FilmrExceptionModel(req, ex);
    } 
}
