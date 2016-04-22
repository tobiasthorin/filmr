package api;

import filmr.Application;
import filmr.domain.Booking;
import filmr.domain.Movie;
import filmr.domain.Showing;
import filmr.domain.Theater;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by luffarvante on 2016-04-22.
 */
public class ApiTest {

    private static ConfigurableApplicationContext applicationContext;
	private String showingApiBaseUrl = "http://localhost:8080/filmr/api/showings";
	private RestTemplate restTemplate;
	private Showing testShowing;

	@BeforeClass // just do this once, for the whole test class (not for each method, like @Before)
	public static void testClassSetup(){
		// start application so that API is running
		applicationContext = SpringApplication.run(Application.class, new String[0]);
	}

	@Before
	public void setup() {
		restTemplate = new RestTemplate();

		//Setup showing
		LocalDateTime time = LocalDateTime.now();
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		Movie movie = new Movie();
		Theater theater = new Theater();

		testShowing = new Showing();
		testShowing.setShowDateTime(time);
		testShowing.setBookings(bookings);
		testShowing.setMovie(movie);
		testShowing.setTheater(theater);
	}


	@Test
	public void testCreate() {
		ResponseEntity<Showing> responseEntity = restTemplate.postForEntity(showingApiBaseUrl, testShowing, Showing.class);
		Showing postedShowing = responseEntity.getBody();

		assertEquals(testShowing.getShowDateTime(), postedShowing.getShowDateTime());
		assertEquals(testShowing.getBookings(), postedShowing.getBookings());
		assertEquals(testShowing.getMovie(), postedShowing.getMovie());
		assertEquals(testShowing.getTheater(), postedShowing.getTheater());
	}

	@Test
	public void testRead() {
		//TODO proper parameter
		Long id = new Long(1);

		ResponseEntity<Showing> readShowingResponseEntity = restTemplate.getForEntity(showingApiBaseUrl+"/"+id, Showing.class);
		Showing readShowing = readShowingResponseEntity.getBody();

		assertEquals(id, readShowing.getId());
	}

	@Test
	public void testUpdate() {
		//TODO actually change a value before update?
		//restTemplate.put(showingApiBaseUrl+"/1", showingToRead); //TODO must have a read valid showing
	}

	@Test
	public void testUpdateWithoutId() {
		//This will send a showing to put WITHOUT ID; it should return a 400 Bad Request
		restTemplate.put(showingApiBaseUrl+"/1", new Showing());
		//TODO actual test method? this returns void, so impossible to make sure its 400 bad request
	}

	@Test
	public void testDelete() {
		restTemplate.delete(showingApiBaseUrl+"/1", Showing.class);
		//TODO actually do a proper test here
	}

    @Test
	public void testIfNoShowingsExist() {
		List<Showing> expectedEmptyList = new ArrayList<Showing>();
		List<Showing> actualListFromEmptyDatabase = (List<Showing>) restTemplate.getForObject(showingApiBaseUrl, List.class);
		assertEquals(expectedEmptyList, actualListFromEmptyDatabase);
	}
}
