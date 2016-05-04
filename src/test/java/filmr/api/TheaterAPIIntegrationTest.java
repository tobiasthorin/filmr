package filmr.api;

import filmr.Application;
import filmr.domain.Cinema;
import filmr.domain.Theater;
import filmr.repositories.CinemaRepository;
import filmr.repositories.TheaterRepository;
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

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest
@ActiveProfiles({"test"})
public class TheaterAPIIntegrationTest {

    //Used instead of SpringJunit4ClassRunner in @RunWith
    private TestContextManager testContextManager;
    //Variables
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    private RestTemplate restTemplate;
    private String baseUrl;
    private String urlWithId;
    //Variables for testing values
    private int tableSize;
    private Theater savedTheater;
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

    public TheaterAPIIntegrationTest(Long id) {
        baseUrl = "http://localhost:8080/filmr/api/theaters/";
    }

    @Before
    public void resetDatabase() throws Exception {
        //Initialize replacement for SpringJunit4ClassRunner
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);

        //Initialize restTemplate
        restTemplate = new RestTemplate(); //TODO TestRestTemplate broken

        //clear everything
        theaterRepository.deleteAllInBatch();
        cinemaRepository.deleteAllInBatch();

        //Create showing and everything that belongs in it
        Cinema cinema = EntityFactory.createCinema("Global Test Cinema");
        savedCinema = cinemaRepository.save(cinema);
        Theater theater = EntityFactory.createTheater("Global Test Theater", savedCinema);
        savedTheater = theaterRepository.save(theater);

        //Setup id for this run
        id = theater.getId();
        urlWithId = baseUrl+id;

        tableSize = theaterRepository.findAll().size();
    }

    @Test
    public void testCreate() throws Exception {
        Theater theater = EntityFactory.createTheater("testCreate Theater", savedCinema);

        //Post
        ResponseEntity<Theater> responseEntity = restTemplate.postForEntity(baseUrl, theater, Theater.class);
        Theater postedTheater = responseEntity.getBody();

        //Assert
        assertTrue("Make sure the http was successfull", responseEntity.getStatusCode().is2xxSuccessful());
        //TODO not allowed to read cinema
        assertEquals("Assert Theater Name", theater.getName(), postedTheater.getName());

        assertEquals("Assert that amount of theaters is +1", tableSize +1, theaterRepository.findAll().size());
    }
}
