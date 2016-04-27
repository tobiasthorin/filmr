package crud;

import filmr.Application;
import filmr.domain.Movie;
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

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * Created by Adrian on 2016-04-27.
 * Excessive comments to help team better understand every step in detail.
 */

@RunWith(Parameterized.class)
public class MovieCRUDTest {

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
    public MovieCRUDTest(Long id) {
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
        baseUrl = "http://localhost:8080/filmr/api/movies";
        urlWithId = baseUrl+"/"+id;
	}

    /*
    Creates a Movie and posts it to database.
    Then compares object returned by post method to original object
    (except ID as the new one got it but not original)
     */
    @Test
    public void testCreate() {
        //Create and setup new movie
        Movie movie = new Movie();
        movie.setTitle("Kung fu murder");
        movie.setDescription("A movie about karate");
        movie.setLengthInMinutes(new Long(120));

        //Post
        ResponseEntity<Movie> responseEntity = restTemplate.postForEntity(baseUrl, movie, Movie.class);
        Movie postedMovie = responseEntity.getBody();

        //Assert
        assertEquals("The movies should be the same or POST is broken", movie.getTitle(), postedMovie.getTitle());
        assertEquals(movie.getDescription(), postedMovie.getDescription());
        assertEquals(movie.getLengthInMinutes(), postedMovie.getLengthInMinutes());
    }

    /*
    Reads object with current ID and checks that the read object has the same ID
     */
    @Test
    public void testRead() {
        //Get
        ResponseEntity<Movie> responseEntity = restTemplate.getForEntity(urlWithId, Movie.class);
        Movie movie = responseEntity.getBody();

        //Assert
        assertEquals(id, movie.getId());
    }

    /*
    Reads an object.
    Changes a field.
    Updates object in database.
    Reads object again (put return type is void)
    Compares read updated object to original one; should not be same.
    Checks if the field updated properly.
     */
    @Test
    public void testUpdate() {
        //Read an object
        ResponseEntity<Movie> responseEntity = restTemplate.getForEntity(urlWithId, Movie.class);
        Movie readMovie = responseEntity.getBody();
        Movie toUpdate = responseEntity.getBody();

        String changeTitleTo = "Bobs bob";
        toUpdate.setTitle(changeTitleTo);

        //Update object in database
        restTemplate.put(urlWithId, toUpdate);

        //Read object again
        responseEntity = restTemplate.getForEntity(urlWithId, Movie.class);
        Movie updatedMovie = responseEntity.getBody();

        //Assert
        assertNotSame("check that the original movie differs from the updated read one", readMovie, updatedMovie);
        assertEquals("check that the updated field is correct", changeTitleTo, updatedMovie.getTitle());
    }

    /*
    Tries to update (put) a null object in database.
    This should not be allowed and throw a 400 Bad Request
     */
    @Test(expected = HttpClientErrorException.class)
    public void testUpdateNull() {
        //Create object
        Movie movie = new Movie();
        //Try to put. Should throw HttpClientErrorException 400 Bad Request
        restTemplate.put(urlWithId, movie);
    }

    /*
    Tries to delete an object.
    Then tries to read it.
    If no object with id exists for delete OR if the object we try to read doesnt exist (it shouldnt as we delete it)
    a HttpServerErrorException 500 Internal Server Error is thrown
     */
    @Test (expected = HttpServerErrorException.class)
    public void testDelete() {
        //Delete object
        restTemplate.delete(urlWithId, Movie.class);
        //Try to read it (should not exist)
        restTemplate.getForEntity(urlWithId, Movie.class);
    }
}
