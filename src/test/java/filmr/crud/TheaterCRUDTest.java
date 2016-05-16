package filmr.crud;

import filmr.Application;
import filmr.domain.Cinema;
import filmr.domain.Row;
import filmr.domain.Showing;
import filmr.domain.Theater;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by luffarvante on 2016-04-28.
 */
public class TheaterCRUDTest {

    //Variables
    private static ConfigurableApplicationContext applicationContext;
    private RestTemplate restTemplate;
    private String baseUrl;
    private String apiUrl;

    //Parameters
    private Long id;

    @BeforeClass
	public static void testClassSetup(){
		applicationContext = SpringApplication.run(Application.class, new String[0]);
	}

    @Before
	public void setup() {
		restTemplate = new RestTemplate();
        baseUrl = "http://localhost:8080/filmr/api/theaters";
        apiUrl = "http://localhost:8080/filmr/api/";
	}

    @Test
    public void testCreate() {
        Theater theater = new Theater();

        ResponseEntity<Cinema> cinemaResponseEntity = restTemplate.getForEntity(apiUrl+"cinemas/"+1, Cinema.class);
        Cinema cinema = cinemaResponseEntity.getBody();
        //TODO only take a row and a showing and puts in a list, to leave as little as possible to horrible chance
        ArrayList<Row> rows = new ArrayList<>(); //TODO this may break in the future if notNull or similar added
        ResponseEntity<Showing> showingResponseEntity = restTemplate.getForEntity(apiUrl+"showings/"+1, Showing.class);
        Showing showing = showingResponseEntity.getBody();
        ArrayList<Showing> showings = new ArrayList<>();
        showings.add(showing);

        //TODO use proper parameters?
        theater.setName("Den fina salen");
        // theater.setNumberOfSeats(100);
        theater.setDisabled(false);
        theater.setRows(rows);
        theater.setCinema(cinema);
        theater.setShowings(showings);

        //Post
        ResponseEntity<Theater> responseEntity = restTemplate.postForEntity(baseUrl, theater, Theater.class);
        Theater postedTheater = responseEntity.getBody();



//        Theater spyTheater = spy(theater);
//        when(spyTheater.getId()).thenReturn(postedTheater.getId());
//
//        System.out.println("id "+postedTheater.getId());
//        System.out.println("sid "+spyTheater.getId());
//
//        Boolean returnedEqualsPosted = postedTheater.equals(spyTheater);
//        System.out.println(postedTheater);
//        System.out.println(spyTheater);
//        assertEquals("bob", postedTheater, spyTheater);
//        assertTrue("assert if posted and object returned from post method is the same", returnedEqualsPosted);
    }

    @Test
    public void testRead() {

    }

    @Test
    public void testUpdate() {

    }

    @Test (expected = HttpClientErrorException.class)
    public void testUpdateWithNull() {

    }

    @Test (expected = HttpServerErrorException.class)
    public void testDelete() {

    }
}
