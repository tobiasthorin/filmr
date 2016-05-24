package filmr.controllers;

import com.jcabi.aspects.Loggable;
import filmr.domain.Movie;
import filmr.helpers.exceptions.FilmrPOSTRequestWithPredefinedIdException;
import filmr.helpers.exceptions.FilmrPUTRequestWithMissingEntityIdException;
import filmr.services.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/movies")
public class MovieController extends BaseController {

    @Autowired
    private MovieService movieService;

    @Loggable
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

    @Loggable
    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Movie> readMovie(@PathVariable Long id){
        Movie retrievedMovie = movieService.readEntity(id);
        return new ResponseEntity<Movie>(retrievedMovie, HttpStatus.OK);
    }

    @Loggable
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

    @Loggable
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

    @Loggable
    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMovie(@PathVariable Long id) {
    	logger.info("deleting movie with id " + id);
        movieService.deleteEntity(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    
}
