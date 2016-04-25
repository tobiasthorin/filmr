package api;

import filmr.Application;
import filmr.domain.Movie;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by luffarvante on 2016-04-25.
 */
public class MovieTest {

    private static ConfigurableApplicationContext applicationContext;
	private String movieApiBaseUrl = "http://localhost:8080/filmr/api/movies";
	private RestTemplate restTemplate;
	private Movie testMovie;

	@BeforeClass // just do this once, for the whole test class (not for each method, like @Before)
	public static void testClassSetup(){
		// start application so that API is running
		applicationContext = SpringApplication.run(Application.class, new String[0]);
	}

	@Before
	public void setup() {
		restTemplate = new RestTemplate();

		//TODO setup testMovie
        testMovie = new Movie();
	}


	@Test
	public void testCreate() {
		//ResponseEntity<Showing> responseEntity = restTemplate.postForEntity(movieApiBaseUrl, testMovie, Showing.class);

		//TODO with current setup its not possible to send anything but an empty object
		Movie movie = new Movie();
		ResponseEntity<Movie> responseEntity = restTemplate.postForEntity(movieApiBaseUrl, movie, Movie.class);
		Movie postedMovie = responseEntity.getBody();

		//TODO assertEquals
	}

	@Test
	public void testRead() {
		//Checks if API returns object with supplied ID
		//TODO proper parameter
		Long id = new Long(1);

		ResponseEntity<Movie> readMovieResponseEntity = restTemplate.getForEntity(movieApiBaseUrl +"/"+id, Movie.class);
		Movie movie = readMovieResponseEntity.getBody();

		assertEquals(id, movie.getId());
	}

	@Test
	public void testUpdate() {
		//TODO proper paramater
		Long id = new Long(1);

		ResponseEntity<Movie> movieResponseEntity = restTemplate.getForEntity(movieApiBaseUrl +"/"+id, Movie.class);
		Movie updateToThis = movieResponseEntity.getBody();

		//TODO update some values in the object here

		restTemplate.put(movieApiBaseUrl +"/"+id, updateToThis);

		//To actually test value, read it from database. This assumes reading has properly been tested and works
		ResponseEntity<Movie> readMovieResponseEntity = restTemplate.getForEntity(movieApiBaseUrl +"/"+id, Movie.class);
		Movie movieFromAPI = readMovieResponseEntity.getBody();

		//TODO assertEquals
	}

	@Test(expected = HttpClientErrorException.class)
	public void testUpdateWithoutId() {
		//TODO proper parameter
		Long id = new Long(1);
		//This will send a showing to put WITHOUT ID; it should return a 400 Bad Request
		restTemplate.put(movieApiBaseUrl + "/"+id, new Movie());
	}

	@Test(expected = HttpServerErrorException.class)
	public void testDelete() {
		//TODO proper parameter
		Long id = new Long(1);
		//Delete object
		restTemplate.delete(movieApiBaseUrl +"/"+id, Movie.class); //TODO is it a fail if there is no object with this ID? Currently not.
		//Try to read object at id; should not work
		restTemplate.getForEntity(movieApiBaseUrl + "/" + id, Movie.class);
	}

    @Test
	public void testIfNoShowingsExist() {
		List<Movie> expectedEmptyList = new ArrayList<Movie>();
		List<Movie> actualListFromEmptyDatabase = (List<Movie>) restTemplate.getForObject(movieApiBaseUrl, List.class);
		assertEquals(expectedEmptyList, actualListFromEmptyDatabase);
	}
}
