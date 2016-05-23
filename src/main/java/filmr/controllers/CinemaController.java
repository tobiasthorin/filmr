package filmr.controllers;

import filmr.domain.Cinema;
import filmr.domain.Repertoire;
import filmr.helpers.exceptions.FilmrInvalidEntityParameterRange;
import filmr.helpers.exceptions.FilmrPOSTRequestWithPredefinedIdException;
import filmr.helpers.exceptions.FilmrPUTRequestWithMissingEntityIdException;
import filmr.services.CinemaService;
import filmr.services.RepertoireService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/cinemas")
public class CinemaController extends BaseController{
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private RepertoireService repertoireService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Cinema> createCinema(@RequestBody Cinema cinema) throws FilmrPOSTRequestWithPredefinedIdException, FilmrInvalidEntityParameterRange {
        if(cinema.getId() != null) {
			logger.warn("Can't create cinema with manually set ID");
        	throw new FilmrPOSTRequestWithPredefinedIdException("Trying to create Cinema, but sending Cinema with predefined id.");
        }
        if(cinema.getName().length() > 48) { //TODO this really should be some global variable?
            logger.warn("Can't create cinema with a name surpassing the limit.");
            throw new FilmrInvalidEntityParameterRange("Trying to create Cinema, but name is to long (limit 48)");
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
        List<Cinema> retrievedCinemas = cinemaService.readAllEntities();
        return new ResponseEntity<List<Cinema>>(retrievedCinemas, HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Cinema> updateCinema(@PathVariable Long id, @RequestBody Cinema cinema) throws FilmrPUTRequestWithMissingEntityIdException, FilmrInvalidEntityParameterRange {
        if(cinema.getId() == null){
			logger.warn("Can only update cinema with a set ID");
        	throw new FilmrPUTRequestWithMissingEntityIdException("Cinema entity to be updated must have a non-null id property");
        }
        if(cinema.getName().length() > 48) {
            logger.warn("Can't create cinema with a name surpassing the limit.");
            throw new FilmrInvalidEntityParameterRange("Trying to create Cinema, but name is to long (limit 48)");
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

