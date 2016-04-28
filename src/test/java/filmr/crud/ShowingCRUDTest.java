package filmr.crud;

import filmr.Application;
import filmr.domain.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.Assert.assertEquals;


/**
 * Created by luffarvante on 2016-04-27.
 */
@RunWith(Parameterized.class)
public class ShowingCRUDTest {

    //Variables
    private static ConfigurableApplicationContext applicationContext;
    private RestTemplate restTemplate;
    private String baseUrl;
    private String urlWithId;

    //Parameters
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

    //Take parameters from parameter list and assign to variable
    public ShowingCRUDTest(Long id) {
        this.id = id;
    }

    @BeforeClass
	public static void testClassSetup(){
		applicationContext = SpringApplication.run(Application.class, new String[0]);
	}

    //This is done before every method
    @Before
	public void setup() {
		restTemplate = new RestTemplate();
        baseUrl = "http://localhost:8080/filmr/api/showings";
        urlWithId = baseUrl+"/"+id;
	}

    @Test
    public void testCreate() {
        //Create and setup new Showing
        Showing showing = new Showing();

        //Showing properties
        Date date = new Date();
        Movie movie;
        Theater theater;
        ArrayList<Booking> bookings;

        //Actually read them from database. Mocking seems impossible.
        String filmrUrl = "http://localhost:8080/filmr/api/";
        ResponseEntity<Movie> movieEntity = restTemplate.getForEntity(filmrUrl+"movies/"+1, Movie.class);
        movie = movieEntity.getBody();
        ResponseEntity<Theater> theaterEntity = restTemplate.getForEntity(filmrUrl+"theaters/"+1, Theater.class);
        theater = theaterEntity.getBody();
        ResponseEntity<ArrayList> bookingsEntity = restTemplate.getForEntity(filmrUrl+"bookings/", ArrayList.class);
        bookings = bookingsEntity.getBody();

        //Setup acutal object
        showing.setShowDateTime(date);
        showing.setMovie(movie);
        showing.setTheater(theater);
        showing.setBookings(bookings);

        //Post
        ResponseEntity<Showing> responseEntity = restTemplate.postForEntity(baseUrl, showing, Showing.class);
        Showing postedShowing = responseEntity.getBody();

        assertEquals(showing.getShowDateTime(), postedShowing.getShowDateTime());
        assertEquals(showing.getMovie(), postedShowing.getMovie());
        assertEquals(showing.getTheater(), postedShowing.getTheater());
        assertEquals(showing.getBookings(), postedShowing.getBookings());
    }

    @Test
    public void testRead() {
        //Get
        ResponseEntity<Showing> responseEntity = restTemplate.getForEntity(urlWithId, Showing.class);
        Showing showing = responseEntity.getBody();

        //Assert
        assertEquals(id, showing.getId());
    }

    @Test
    public void testUpdate() {
        //Read an object
        ResponseEntity<Showing> responseEntity = restTemplate.getForEntity(urlWithId, Showing.class);
        Showing toUpdate = responseEntity.getBody();

        //TODO update
        String filmrUrl = "http://localhost:8080/filmr/api/";
        ResponseEntity<Movie> movieResponseEntity = restTemplate.getForEntity(filmrUrl+"movies/"+3, Movie.class);
        Movie updateToThisMovie = movieResponseEntity.getBody();
        toUpdate.setMovie(updateToThisMovie);

        //Update object in database
        restTemplate.put(urlWithId, toUpdate);

        //Read object again
        responseEntity = restTemplate.getForEntity(urlWithId, Showing.class);
        Showing updatedShowing = responseEntity.getBody();

        //Assert
        assertEquals(toUpdate, updatedShowing);
        assertEquals(updateToThisMovie, updatedShowing.getMovie());
    }

    @Test(expected = HttpClientErrorException.class)
    public void testUpdateNull() {
        //Create object
        Showing showing = new Showing();
        //Try to put. Should throw HttpClientErrorException 400 Bad Request
        restTemplate.put(urlWithId, showing);
    }

    @Test (expected = HttpServerErrorException.class)
    public void testDelete() {
        //Delete object
        restTemplate.delete(urlWithId, Showing.class);
        //Try to read it (should not exist)
        restTemplate.getForEntity(urlWithId, Showing.class);
    }
}
