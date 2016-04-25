package api;

import filmr.Application;
import filmr.domain.Booking;
import filmr.domain.Movie;
import filmr.domain.Showing;
import filmr.domain.Theater;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by luffarvante on 2016-04-22.
 */
public class ApiTest {

    private static ConfigurableApplicationContext applicationContext;
	private String showingApiBaseUrl = "http://localhost:8080/filmr/api/showings";
	private RestTemplate restTemplate;

	@BeforeClass // just do this once, for the whole test class (not for each method, like @Before)
	public static void testClassSetup(){
		// start application so that API is running
		applicationContext = SpringApplication.run(Application.class, new String[0]);
	}

	@Before
	public void setup() {
		restTemplate = new RestTemplate();
	}


	@Test
	public void testCreate() {
		//ResponseEntity<Showing> responseEntity = restTemplate.postForEntity(showingApiBaseUrl, testShowing, Showing.class);

		//TODO with current setup its not possible to send anything but an empty object
		Showing s = new Showing();
		ResponseEntity<Showing> responseEntity = restTemplate.postForEntity(showingApiBaseUrl, s, Showing.class);
		Showing postedShowing = responseEntity.getBody();

		assertEquals(s.getShowDateTime(), postedShowing.getShowDateTime());
		assertEquals(s.getBookings(), postedShowing.getBookings());
		assertEquals(s.getMovie(), postedShowing.getMovie());
		assertEquals(s.getTheater(), postedShowing.getTheater());

//		assertEquals(testShowing.getShowDateTime(), postedShowing.getShowDateTime());
//		assertEquals(testShowing.getBookings(), postedShowing.getBookings());
//		assertEquals(testShowing.getMovie(), postedShowing.getMovie());
//		assertEquals(testShowing.getTheater(), postedShowing.getTheater());
	}

	@Test
	public void testRead() {
		//Checks if API returns object with supplied ID
		//TODO proper parameter
		Long id = new Long(1);

		ResponseEntity<Showing> readShowingResponseEntity = restTemplate.getForEntity(showingApiBaseUrl+"/"+id, Showing.class);
		Showing readShowing = readShowingResponseEntity.getBody();

		assertEquals(id, readShowing.getId());
	}

	@Test
	public void testUpdate() {
		//TODO proper paramater
		Long id = new Long(1);

		ResponseEntity<Showing> showingResponseEntity = restTemplate.getForEntity(showingApiBaseUrl+"/"+id, Showing.class);
		Showing updateToThis = showingResponseEntity.getBody();

		//TODO update some values in the object here

		restTemplate.put(showingApiBaseUrl+"/"+id, updateToThis);

		//To actually test value, read it from database. This assumes reading has properly been tested and works
		ResponseEntity<Showing> readShowingResponseEntity = restTemplate.getForEntity(showingApiBaseUrl+"/"+id, Showing.class);
		Showing showingFromAPI = readShowingResponseEntity.getBody();

		//TODO override equals method
		//assertEquals(updateToThis, showingFromAPI);
		assertEquals(updateToThis.getId(), showingFromAPI.getId());
		assertEquals(updateToThis.getMovie(), showingFromAPI.getMovie());
		assertEquals(updateToThis.getShowDateTime(), showingFromAPI.getShowDateTime());
		assertEquals(updateToThis.getBookings(), showingFromAPI.getBookings());
		assertEquals(updateToThis.getTheater(), showingFromAPI.getTheater());
	}

	@Test(expected = HttpClientErrorException.class)
	public void testUpdateWithoutId() {
		//TODO proper parameter
		Long id = new Long(1);
		//This will send a showing to put WITHOUT ID; it should return a 400 Bad Request
		restTemplate.put(showingApiBaseUrl + "/"+id, new Showing());
	}

	@Test(expected = HttpServerErrorException.class)
	public void testDelete() {
		//TODO proper parameter
		Long id = new Long(1);
		//Delete object
		restTemplate.delete(showingApiBaseUrl+"/"+id, Showing.class);
		//Try to read object at id; should not work
		ResponseEntity<Showing> showingResponseEntity = restTemplate.getForEntity(showingApiBaseUrl + "/" + id, Showing.class);
		Showing showing = showingResponseEntity.getBody();
	}

    @Test
	public void testIfNoShowingsExist() {
		List<Showing> expectedEmptyList = new ArrayList<Showing>();
		List<Showing> actualListFromEmptyDatabase = (List<Showing>) restTemplate.getForObject(showingApiBaseUrl, List.class);
		assertEquals(expectedEmptyList, actualListFromEmptyDatabase);
	}
}
