package filmr.api;

import filmr.Application;
import filmr.domain.Cinema;
import filmr.domain.Theater;
import filmr.repositories.CinemaRepository;
import filmr.repositories.TheaterRepository;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

//TODO when run after other tests it breaks (showing api integration test)

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
        restTemplate = new RestTemplate();

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
    	
    	// TODO: fix test with params ?number_of_rows=2&max_row_size=4
    	HashMap<String,String> parameters = new HashMap<String, String>();
    	parameters.put("number_of_rows", "4");
    	parameters.put("max_row_size", "5");
    	
    	
    	URI urlWithParams = getURI(parameters);
    	System.out.println("sending uri params: " + urlWithParams );
    	
        Theater theater = EntityFactory.createTheater("testCreate Theater", savedCinema);

        //Post
        ResponseEntity<Theater> responseEntity = restTemplate.postForEntity(urlWithParams, theater, Theater.class);
        Theater postedTheater = responseEntity.getBody();
        System.out.println("posted theater is: " + postedTheater);

        //Assert
        assertTrue("Make sure the http was successfull", responseEntity.getStatusCode().is2xxSuccessful());
        //TODO not allowed to read cinema
        assertEquals("Assert Theater Name", theater.getName(), postedTheater.getName());

        assertEquals("Assert that amount of theaters is +1", tableSize +1, theaterRepository.findAll().size());
    }

    @Test
    public void testRead() {
        ResponseEntity<Theater> responseEntity = restTemplate.getForEntity(urlWithId, Theater.class);
        Theater theater = responseEntity.getBody();


        //Assert
        assertTrue("Make sure the call was succesfull", responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("Assert that the id of the read object is the same as we asked to get", id, theater.getId());
        assertEquals("Assert that the read object is the same as the one created in @Before", savedTheater, theater);
    }

    @Test
    public void testUpdate() throws Exception {

    	// setup values to update to
    	String updatedTheaterName = "Test updated theater name";

    	// make the changes to the local, but previously saved, java object
    	savedTheater.setName(updatedTheaterName);
    	// savedTheater.setNumberOfSeats(updatedNumberOfSeats);

    	// actually do the PUT request
    	restTemplate.put(urlWithId, savedTheater);

    	// get the (hopefully) updated theater from repo
    	Theater updatedTheater = theaterRepository.findOne(id);

    	// test if the local changes (savedTheater) matches the actual ones (updatedTheater)
    	assertEquals("Assert that the updated theater matches the changes we made", savedTheater, updatedTheater);
    }

    @After
    public void clearDatabase() throws Exception {
        //clear everything
        theaterRepository.deleteAllInBatch();
        cinemaRepository.deleteAllInBatch();
    }
    
    private URI getURI (HashMap<String, String> params) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        
        for(Entry<String, String> entry : params.entrySet()) {
        	uriComponentsBuilder.queryParam(entry.getKey(), entry.getValue());
        }
        UriComponents uriComponents = uriComponentsBuilder.build();
        return uriComponents.toUri();
    }
}
