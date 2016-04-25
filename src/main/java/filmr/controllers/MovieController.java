package filmr.controllers;

import filmr.domain.Movie;
import filmr.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Marco on 2016-04-22.
 */
@RestController
@RequestMapping(value = "/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
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
    public ResponseEntity<List<Movie>> readAllMovies() {
        List<Movie> retrievedMovies = movieService.readAllEntities();
        return new ResponseEntity<List<Movie>>(retrievedMovies, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie){
        if(movie.getId() == null){
            return new ResponseEntity<Movie>(new Movie(), HttpStatus.BAD_REQUEST);
        }

        Movie updatedMovie = movieService.saveEntity(movie);
        return new ResponseEntity<Movie>(updatedMovie, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMovie(@PathVariable Long id) {
        movieService.deleteEntity(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
