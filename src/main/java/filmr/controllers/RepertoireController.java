package filmr.controllers;

import filmr.domain.Repertoire;
import filmr.services.RepertoireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Marco on 2016-04-26.
 */
@RestController
@RequestMapping(value = "/api/repertoires")
public class RepertoireController {
    @Autowired
    private RepertoireService repertoireService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Repertoire> createRepertoire(@RequestBody Repertoire repertoire) {
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
    public ResponseEntity<List<Repertoire>> readAllMovies() {
        List<Repertoire> retrievedRepertoires = repertoireService.readAllEntities();
        return new ResponseEntity<List<Repertoire>>(retrievedRepertoires, HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Repertoire> updateRepertoire(@PathVariable Long id, @RequestBody Repertoire repertoire){
        if(repertoire.getId() == null){
            return new ResponseEntity<Repertoire>(new Repertoire(), HttpStatus.BAD_REQUEST);
        }

        Repertoire updatedRepertoire = repertoireService.saveEntity(repertoire);
        return new ResponseEntity<Repertoire>(updatedRepertoire, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRepertoire(@PathVariable Long id) {
        repertoireService.deleteEntity(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}

