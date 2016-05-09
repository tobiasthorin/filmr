package filmr.api;

import filmr.Application;
import filmr.domain.Movie;
import filmr.repositories.MovieRepository;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest
@ActiveProfiles({"test"})
public class MovieAPIIntegrationTest {

    //Used instead of SpringJunit4ClassRunner in @RunWith
    private TestContextManager testContextManager;
    //Variables
    @Autowired
    private MovieRepository movieRepository;
    private RestTemplate restTemplate;
    private String baseUrl;
    private String urlWithId;
    //Variables for testing values
    private int tableSize;
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

    public MovieAPIIntegrationTest(Long id) {
        baseUrl = "http://localhost:8080/filmr/api/movies/";
    }

    @Before
	public void resetDatabase() throws Exception {
        //Initialize replacement for SpringJunit4ClassRunner
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);

        //Initialize restTemplate
        restTemplate = new RestTemplate();

        //clear everything
        movieRepository.deleteAllInBatch();

        //Create showing and everything that belongs in it
        Movie movie = EntityFactory.createMovie("Global Test Movie", "A movie about things", new Long(120), new Double(100));
        savedMovie = movieRepository.save(movie);

        //Setup id for this run
        id = savedMovie.getId();
        urlWithId = baseUrl+id;

        tableSize = movieRepository.findAll().size();
    }

    @Test
    public void testCreate() throws Exception {
        Movie movie = EntityFactory.createMovie("testCreate Movie", "A movie about testing", new Long(110), new Double(100));

        //Post
        ResponseEntity<Movie> responseEntity = restTemplate.postForEntity(baseUrl, movie, Movie.class);
        Movie postedMovie = responseEntity.getBody();

        //Assert
        assertTrue("Make sure the http was successfull", responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("The movie titles should be the same or POST is broken", movie.getTitle(), postedMovie.getTitle());
        assertEquals("Assert that movie descriptions are the same", movie.getDescription(), postedMovie.getDescription());
        assertEquals("Assert that movie lenght is the same", movie.getLengthInMinutes(), postedMovie.getLengthInMinutes());

        assertEquals("Assert that amount of movies is +1", tableSize +1, movieRepository.findAll().size());
    }

    @Test
    public void testRead() {
        //Get
        ResponseEntity<Movie> responseEntity = restTemplate.getForEntity(urlWithId, Movie.class);
        Movie movie = responseEntity.getBody();

        //Assert
        assertEquals("Assert that the id of the read object is the same as we asked to get", id, movie.getId());
        assertEquals("Assert that the read object is the same as the one created in @Before", savedMovie, movie);
    }

    @Test
    public void testUpdate() {
        String changeTitleTo = "Bobs bob";
        savedMovie.setTitle(changeTitleTo);

        //Update object in database
        restTemplate.put(urlWithId, savedMovie);

        Movie updatedMovie = movieRepository.findOne(id);
//        ResponseEntity<Movie> responseEntity = restTemplate.getForEntity(urlWithId, Movie.class);
//        Movie updatedMovie = responseEntity.getBody();

        //Assert
        assertEquals("Assert that the object is updated", savedMovie, updatedMovie);
        assertEquals("check that the updated field is correct", changeTitleTo, updatedMovie.getTitle());
    }

    @Test(expected = HttpClientErrorException.class)
    public void testUpdateNull() {
        //Create object
        Movie movie = new Movie();
        //Try to put. Should throw HttpClientErrorException 400 Bad Request
        restTemplate.put(urlWithId, movie);
    }

    @Test (expected = HttpServerErrorException.class)
    public void testDelete() {
        //Delete object
        restTemplate.delete(urlWithId, Movie.class);
        //Try to read it (should not exist)
        restTemplate.getForEntity(urlWithId, Movie.class);
    }

    @After
    public void clearDatabase() throws Exception {
        //clear everything
        movieRepository.deleteAllInBatch();
    }
}
