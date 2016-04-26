package api;

import filmr.Application;
import filmr.domain.Booking;
import filmr.domain.Movie;
import filmr.domain.Showing;
import filmr.domain.Theater;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.junit.Assert.fail;

/**
 * Created by luffarvante on 2016-04-25.
 */
@RunWith(Parameterized.class)
public class ShowingTest {

    private static ConfigurableApplicationContext applicationContext;
	private String showingApiBaseUrl = "http://localhost:8080/filmr/api/showings";
	private RestTemplate restTemplate;
    private Long id;

    @Parameterized.Parameters
    public static Collection<Object[]> ids() {
        return Arrays.asList(new Object[][]{
                {new Long(1)},
                {new Long(2)},
                {new Long(3)},
                {new Long(4)},
                {new Long(5)},
                {new Long(6)},
                {new Long(7)},
                {new Long(8)},
                {new Long(9)},
                {new Long(10)},
        });
    }

    public ShowingTest(Long id) {
        this.id = id;
    }

	@BeforeClass // just do this once, for the whole test class (not for each method, like @Before)
	public static void testClassSetup(){
		/* start application so that API is running */
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
	}



    @Test
	public void testRead() {
		//Checks if API returns object with supplied ID
		ResponseEntity<Showing> readShowingResponseEntity = restTemplate.getForEntity(showingApiBaseUrl+"/"+id, Showing.class);
		Showing readShowing = readShowingResponseEntity.getBody();

		assertEquals(id, readShowing.getId());
	}

	@Test
	public void testUpdate() {
		ResponseEntity<Showing> showingResponseEntity = restTemplate.getForEntity(showingApiBaseUrl+"/"+id, Showing.class);
		Showing updateToThis = showingResponseEntity.getBody();

		//TODO update some values in the object here
        //updateToThis.setMovie(new Movie());

		restTemplate.put(showingApiBaseUrl+"/"+id, updateToThis);

		//To actually test value, read it from database. This assumes reading has properly been tested and works
		ResponseEntity<Showing> readShowingResponseEntity = restTemplate.getForEntity(showingApiBaseUrl+"/"+id, Showing.class);
		Showing showingFromAPI = readShowingResponseEntity.getBody();

		//TODO override equals method
		//assertEquals(updateToThis, showingFromAPI);
		assertEquals(updateToThis.getId(), showingFromAPI.getId());
//		assertEquals(updateToThis.getMovie(), showingFromAPI.getMovie());
		assertEquals(updateToThis.getShowDateTime(), showingFromAPI.getShowDateTime());
		assertEquals(updateToThis.getBookings(), showingFromAPI.getBookings());
//		assertEquals(updateToThis.getTheater(), showingFromAPI.getTheater());
	}

	@Test(expected = HttpClientErrorException.class)
	public void testUpdateWithoutId() {
		//This will send a showing to put WITHOUT ID; it should return a 400 Bad Request
		restTemplate.put(showingApiBaseUrl + "/"+id, new Showing());
	}

	@Test(expected = HttpServerErrorException.class)
	public void testDelete() {
		//Delete object
		restTemplate.delete(showingApiBaseUrl+"/"+id, Showing.class);
		//Try to read object at id; should not work. If object does not exist, throws a 500 HttpServerErrorException
		restTemplate.getForEntity(showingApiBaseUrl + "/" + id, Showing.class);
	}
}
