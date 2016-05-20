package filmr.controllers;

import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import filmr.domain.Movie;
import filmr.domain.Repertoire;
import filmr.helpers.exceptions.FilmrBaseException;
import filmr.helpers.exceptions.FilmrExceptionModel;
import filmr.helpers.exceptions.FilmrPOSTRequestWithPredefinedIdException;
import filmr.helpers.exceptions.FilmrPUTRequestWithMissingEntityIdException;
import filmr.services.MovieService;
import filmr.services.RepertoireService;

@RestController
@RequestMapping(value = "/api/repertoires")
public class RepertoireController {
    @Autowired
    private RepertoireService repertoireService;
    @Autowired
    private MovieService movieService;
	private final static org.apache.log4j.Logger logger = Logger.getLogger(TheaterController.class);

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Repertoire> createRepertoire(@RequestBody Repertoire repertoire) throws FilmrPOSTRequestWithPredefinedIdException {
        
    	if(repertoire.getClass() != null) throw new FilmrPOSTRequestWithPredefinedIdException("Trying to create Repertoire, but entity id is already set.");
    	
        Repertoire savedRepertoire = repertoireService.saveEntity(repertoire);
        return new ResponseEntity<Repertoire>(savedRepertoire, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Repertoire> readRepertoire(@PathVariable Long id){
        Repertoire retrievedRepertoire = repertoireService.readEntity(id);
        return new ResponseEntity<Repertoire>(retrievedRepertoire, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Repertoire>> readAllRepertoires() {
        List<Repertoire> retrievedRepertoires = repertoireService.readAllEntities();
        return new ResponseEntity<List<Repertoire>>(retrievedRepertoires, HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Repertoire> updateRepertoire(
    		@PathVariable Long id, 
    		@RequestBody Repertoire repertoire,
    		@RequestParam(name="add_movie_with_id", required = false) Long add_movie_with_id,
    		@RequestParam(name="remove_movie_with_id", required = false) Long remove_movie_with_id
    		) throws FilmrPUTRequestWithMissingEntityIdException{
    	
    	if(repertoire.getId() == null ) throw new FilmrPUTRequestWithMissingEntityIdException("Trying to update Repertoire, but entity has no id.");
        
        // needed because .movies will be empty otherwise (see READ_ONLY above)
        repertoire = repertoireService.readEntity(repertoire.getId());
        
        repertoire = updateRepertoireMoviesIfNeeded(repertoire, add_movie_with_id, remove_movie_with_id);

        Repertoire updatedRepertoire = repertoireService.saveEntity(repertoire);
        return new ResponseEntity<Repertoire>(updatedRepertoire, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRepertoire(@PathVariable Long id) {
        repertoireService.deleteEntity(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    private Repertoire updateRepertoireMoviesIfNeeded(Repertoire repertoire, Long add_movie_with_id, Long remove_movie_with_id) {
        
    	logger.info("Updating repertoire with id: " + repertoire.getId());
    	
    	if(repertoire.getMovies() == null) repertoire.setMovies(new HashSet<Movie>());
    	
    	if(add_movie_with_id != null) {
        	Movie movieToAdd = movieService.readEntity(add_movie_with_id);
        	System.out.println("adding movie: " + movieToAdd);
        	repertoire.getMovies().add(movieToAdd);
        }
        if(remove_movie_with_id != null) {
        	Movie movieToRemove = movieService.readEntity(remove_movie_with_id);
        	repertoire.getMovies().remove(movieToRemove);
        }
        
        return repertoire;
    }
    
    // all custom errors should inherit from FilmrBaseException, so this should work for all of them. 
    @ExceptionHandler(FilmrBaseException.class)
    @ResponseBody
    public FilmrExceptionModel handleBadRequest(HttpServletRequest req, FilmrBaseException ex) {
    	logger.debug("Catching custom error in controller.. ");
        return new FilmrExceptionModel(req, ex);
    } 

}

