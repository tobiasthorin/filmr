package api;

import filmr.Application;
import filmr.domain.Showing;
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
	public void testRead() {

		//Post showing to database
		Showing showingToPost = new Showing();
		ResponseEntity<Showing> responseEntity = restTemplate.postForEntity(showingApiBaseUrl, showingToPost, Showing.class);
//		assertEquals(showingToPost, responseEntity.getBody()); //TODO must compare actual values properly OR create equals method

		//Update showing in database
//		Showing updateShowingTo = new Showing();
//		restTemplate.put(showingApiBaseUrl, showingToPost, 1);

//		//Read showing in database
//		ResponseEntity<Showing> readShowingResponseEntity = restTemplate.getForEntity(showingApiBaseUrl, Showing.class,1);
//		Showing showingToRead = readShowingResponseEntity.getBody();
//		assertEquals(showingToPost, showingToRead);

		// check that an empty
		List<Showing> expectedEmptyList = new ArrayList<Showing>();

		List<Showing> actualListFromEmptyDatabase = (List<Showing>) restTemplate.getForObject(showingApiBaseUrl, List.class);
		assertEquals(expectedEmptyList, actualListFromEmptyDatabase);





		
//		// insert some data through api, then test that we can retrieve it
//		Showing actualShowingToSend = new Showing();
//		actualShowingToSend.setShowDateTime(LocalDateTime.now());
//
//		// set json header befor posting
//		RequestEntity<Showing> showingRequestEntity
//			= new RequestEntity<Showing>(actualShowingToSend, HttpMethod.POST, URI.create(showingApiBaseUrl));
//		// showingRequestEntity.getHeaders().setContentType(MediaType.APPLICATION_JSON);
//
//		Showing savedShowing = restTemplate.exchange(showingRequestEntity, Showing.class).getBody();
//		// check that first saved showing gets id 1
//		assertEquals(new Long(1), savedShowing.getId());
//
//		// check that the saved showing has same date as the one we sent
//		assertEquals(actualShowingToSend.getShowDateTime(), savedShowing.getShowDateTime());


	}
}
