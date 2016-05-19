package filmr.controllers;

import filmr.domain.Cinema;
import filmr.domain.Repertoire;
import filmr.helpers.exceptions.IllegalEntityPropertyException;
import filmr.helpers.exceptions.InsufficientEntityDataException;
import filmr.services.CinemaService;
import filmr.services.RepertoireService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/cinemas")
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private RepertoireService repertoireService;
	private final static org.apache.log4j.Logger logger = Logger.getLogger(TheaterController.class);

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Cinema> createCinema(@RequestBody Cinema cinema) throws IllegalEntityPropertyException {
        if(cinema.getId() != null) {
            // return new ResponseEntity<Cinema>(new Cinema(), HttpStatus.BAD_REQUEST);
			logger.warn("Can't create cinema with manually set ID");
        	throw new IllegalEntityPropertyException("Trying to create entity, but sending entity with predefined id.");
        }
        Repertoire repertoire = new Repertoire();
        repertoireService.saveEntity(repertoire);

        cinema.setRepertoire(repertoire);
        Cinema savedCinema = cinemaService.saveEntity(cinema);
        return new ResponseEntity<Cinema>(savedCinema, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cinema> readCinema(@PathVariable Long id){
        Cinema retrievedCinema = cinemaService.readEntity(id);
        return new ResponseEntity<Cinema>(retrievedCinema, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Cinema>> readAllCinemas(@RequestParam(name="show_disabled_cinemas", required=false, defaultValue = "true") Boolean show_disabled_cinemas) {
    //public ResponseEntity<List<Cinema>> readAllMovies(@RequestParam(name="show_disabled_cinemas", required=false, defaultValue = "true") Boolean show_disabled_cinemas) {


        List<Cinema> retrievedCinemas = cinemaService.readAllEntities();
        return new ResponseEntity<List<Cinema>>(retrievedCinemas, HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Cinema> updateCinema(@PathVariable Long id, @RequestBody Cinema cinema) throws InsufficientEntityDataException{
        if(cinema.getId() == null){
            // return new ResponseEntity<Cinema>(new Cinema(), HttpStatus.BAD_REQUEST);
			logger.warn("Can only update cinema with a set ID");
        	throw new InsufficientEntityDataException("Cinema entity to be updated must have a non-null id property");
        }
        Cinema updatedCinema = cinemaService.saveEntity(cinema);
        return new ResponseEntity<Cinema>(updatedCinema, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCinema(@PathVariable Long id) {
        cinemaService.deleteEntity(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}

