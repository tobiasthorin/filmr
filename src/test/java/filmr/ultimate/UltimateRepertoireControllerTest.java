package filmr.ultimate;

import filmr.Application;
import filmr.domain.Movie;
import filmr.domain.Repertoire;
import filmr.repositories.MovieRepository;
import filmr.repositories.RepertoireRepository;
import filmr.testfactories.EntityFactory;
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
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest
@ActiveProfiles({"test"})
public class UltimateRepertoireControllerTest {

    //Used instead of SpringJunit4ClassRunner in @RunWith
    private TestContextManager testContextManager;
    //Variables
    @Autowired
    private RepertoireRepository repertoireRepository;
    @Autowired
    private MovieRepository movieRepository;
    private RestTemplate restTemplate;
    private String baseUrl;
    private String urlWithId;
    //Variables for testing values
    private int tableSize;
    private Repertoire savedRepertoire;
    private Movie savedMovie;

    //Parameters
    private Long id;

    //ID, ? TODO parameters pointless for this test
    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {new Long(1)},
        });
    }

    public UltimateRepertoireControllerTest(Long id) {
        baseUrl = "http://localhost:8080/filmr/api/repertoires/";
    }

    @Before
    public void resetDatabase() throws Exception {
        //Initialize replacement for SpringJunit4ClassRunner
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);

        //Initialize restTemplate
        restTemplate = new RestTemplate(); //TODO TestRestTemplate broken

        //clear everything
        repertoireRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();

        //Create showing and everything that belongs in it
        Repertoire repertoire = EntityFactory.createRepertoire();
        savedRepertoire = repertoireRepository.save(repertoire);
        Movie movie = EntityFactory.createMovie("Global Test Movie", "Global test Movie Description", new Long(120));
        savedMovie = movieRepository.save(movie);

        //Setup id for this run
        id = savedRepertoire.getId();
        urlWithId = baseUrl+id;

        tableSize = repertoireRepository.findAll().size();
    }

    @Test
    public void testCreate() throws Exception {
        Repertoire repertoire = EntityFactory.createRepertoire();

        //Post
        ResponseEntity<Repertoire> responseEntity = restTemplate.postForEntity(baseUrl, repertoire, Repertoire.class);
        Repertoire postedRepertoire = responseEntity.getBody();

        //Assert
        assertNotNull("", postedRepertoire.getId());
        assertNull("", postedRepertoire.getMovies());
        assertEquals("Assert that amount of repertoires is +1", tableSize +1, repertoireRepository.findAll().size());
    }

    @Test
    public void addMovieToRepertoire() {
        List<Movie> repertoireMovies = savedRepertoire.getMovies();
        int repertoireMovieListSize = repertoireMovies.size();
        repertoireMovies.add(savedMovie);
        savedRepertoire.setMovies(repertoireMovies);

        restTemplate.put(urlWithId, savedRepertoire);

        ResponseEntity<Repertoire> responseEntity = restTemplate.getForEntity(baseUrl, Repertoire.class);
        Repertoire updatedRepertoire = responseEntity.getBody();

        //Assert
        assertEquals("Assert that the repertoire updated properly", savedRepertoire, updatedRepertoire);
        assertEquals("Assert that amount of movies in repertoire is +1", repertoireMovieListSize+1, updatedRepertoire.getMovies().size());
    }
}