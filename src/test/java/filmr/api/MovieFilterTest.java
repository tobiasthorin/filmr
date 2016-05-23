package filmr.api;

import filmr.Application;
import filmr.domain.Movie;
import filmr.domain.Repertoire;
import filmr.repositories.MovieRepository;
import filmr.repositories.RepertoireRepository;
import filmr.testfactories.EntityFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest
@ActiveProfiles({"test"})
public class MovieFilterTest {

    //Variables
    @Autowired
    private RepertoireRepository repertoireRepository;
    @Autowired
    private MovieRepository movieRepository;
    private RestTemplate restTemplate;
    private String repetoireBaseUrl;
    private String movieBaseUrl;
    private String urlWithId;
    //Variables for testing values

    private Repertoire savedRepertoire;
    private Movie savedMovie;

    //Parameters
    private Long id;

    @Before
    public void resetDatabase() throws Exception {
        repetoireBaseUrl = "http://localhost:8080/filmr/api/repertoires/";
	    movieBaseUrl = "http://localhost:8080/filmr/api/movies/";

        //Initialize restTemplate
        restTemplate = new RestTemplate();

        //clear everything
        repertoireRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();

        //Create showing and everything that belongs in it
        Repertoire repertoire = EntityFactory.createRepertoire();
        savedRepertoire = repertoireRepository.save(repertoire);
        Movie movie = EntityFactory.createMovie("Global Test Movie", "Global test Movie Description", new Long(120), new Double(100));
        savedMovie = movieRepository.save(movie);

        //Setup id for this run
        id = savedRepertoire.getId();
        urlWithId = repetoireBaseUrl+id;
    }

    @Test
    public void testReadAll() {
        ResponseEntity<Movie[]> responseEntity = restTemplate.getForEntity(movieBaseUrl, Movie[].class);
	    Movie[] movies = responseEntity.getBody();
        assertEquals("Assert that movie service return list of movies", movies.length, 1);
    }


    @Test
    public void testReadAllWithFilterOnRepetoire() {
	    Movie[] moviesBeforeAddToRepetoire = restTemplate.getForEntity(movieBaseUrl+"?not_in_repertoire_with_id="+id, Movie[].class).getBody();
        assertEquals("Assert that query has movie before test of add movie to repetoire", moviesBeforeAddToRepetoire.length, 1);

        Set<Movie> repertoireMovies = savedRepertoire.getMovies();
        int repertoireMovieListSize = repertoireMovies.size();
        repertoireMovies.add(savedMovie);
        savedRepertoire.setMovies(repertoireMovies);

        restTemplate.put(urlWithId+"?add_movie_with_id="+savedMovie.getId(), savedRepertoire);

	    Movie[] moviesAfterAddToRepetoire = restTemplate.getForEntity(movieBaseUrl+"?not_in_repertoire_with_id="+id, Movie[].class).getBody();
        assertEquals("Assert that query is empty since now the movie is already in repetoire", moviesAfterAddToRepetoire.length, 0);

    }

    @After
    public void clearDatabase() throws Exception {
        //clear everything
        repertoireRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();
    }
}
