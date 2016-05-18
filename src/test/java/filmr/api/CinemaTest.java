package filmr.api;

import filmr.Application;
import filmr.domain.Cinema;
import filmr.domain.Movie;
import filmr.domain.Repertoire;
import filmr.repositories.CinemaRepository;
import filmr.testfactories.EntityFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestContextManager;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest
@ActiveProfiles({"test"})
public class CinemaTest {

    //Used instead of SpringJunit4ClassRunner in @RunWith
    private TestContextManager testContextManager;
    //Variables
    @Autowired
    private CinemaRepository cinemaRepository;
    private RestTemplate restTemplate;
    private String baseUrl;
    private String urlWithId;
    //Variables for testing values
    private int tableSize;
    private Cinema savedCinema;

    //Parameters
    private Long id;

    //ID, ? TODO parameters pointless for this test
    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {new Long(1)},
        });
    }

    public CinemaTest(Long id) {
        baseUrl = "http://localhost:8080/filmr/api/cinemas/";
    }

    @Before
    public void resetDatabase() throws Exception {
        //Initialize replacement for SpringJunit4ClassRunner
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);

        //Initialize restTemplate
        restTemplate = new RestTemplate();

        //clear everything
        cinemaRepository.deleteAllInBatch();

        //Create showing and everything that belongs in it
        Cinema cinema = EntityFactory.createCinema("Global Test Cinema");
        savedCinema = cinemaRepository.save(cinema);

        //Setup id for this run
        id = savedCinema.getId();
        urlWithId = baseUrl+id;

        tableSize = cinemaRepository.findAll().size();
    }

    @Test
    public void testCreate() throws Exception {
        Cinema cinema = EntityFactory.createCinema("testCreate Cinema");

        //Post
        ResponseEntity<Cinema> responseEntity = restTemplate.postForEntity(baseUrl, cinema, Cinema.class);
        Cinema postedCinema = responseEntity.getBody();

        //Assert
        assertTrue("Make sure the http was successfull", responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("The cinema name should be the same or POST is broken", cinema.getName(), postedCinema.getName());

        Repertoire postedCinemaRepertoire = postedCinema.getRepertoire();
        assertTrue("Assert that cinema have a repertoire on create", postedCinemaRepertoire!=null);
        assertTrue("Assert that cinemas repertoire is empty", postedCinemaRepertoire.getMovies().size()==0);

        assertEquals("Assert that amount of cinemas is +1", tableSize +1, cinemaRepository.findAll().size());
    }

    @Test(expected = HttpClientErrorException.class)
    public void testCreateWithId() {
        Cinema cinema = savedCinema;

        ResponseEntity<Cinema> responseEntity = restTemplate.postForEntity(baseUrl, cinema, Cinema.class);
        Cinema postedCinema = responseEntity.getBody();

        assertEquals("Make sure we get a bad request", HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testRead() {
        ResponseEntity<Cinema> responseEntity = restTemplate.getForEntity(urlWithId, Cinema.class);
        Cinema cinema = responseEntity.getBody();

        assertTrue("Confirm that the status code is successful", responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("Assert that the id of the read object is the one we requested", id, cinema.getId());
        assertEquals("Assert that the read object is the same as the one created in @Before", savedCinema, cinema);
    }

    @Test
    public void testUpdate() {
        String changeNameTo = "Bobs Bio";
        savedCinema.setName(changeNameTo);

        restTemplate.put(urlWithId, savedCinema);

        Cinema updatedCinema = cinemaRepository.findOne(id);

        assertEquals("Assert that the object is updated properly", savedCinema, updatedCinema);
    }

    @After
    public void clearDatabase() throws Exception {
        //clear everything
        cinemaRepository.deleteAllInBatch();
    }
}
