package filmr.controllers;

import filmr.Application;
import filmr.domain.Cinema;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.boot.SpringApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Marco on 2016-04-27.
 */
@RunWith(Parameterized.class)
public class CinemaControllerTest {

    private RestTemplate restTemplate;
    private String cinemaApiBaseUrl ="http://localhost:8080/filmr/api/cinemas";
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

    public CinemaControllerTest(Long id) {
        this.id = id;
    }

    @BeforeClass // just do this once, for the whole test class (not for each method, like @Before)
    public static void testClassSetup(){
		/* start application so that API is running */
        SpringApplication.run(Application.class, new String[0]);

    }

    @Before
    public void setUp() throws Exception {
        restTemplate = new RestTemplate();
        urlWithId = cinemaApiBaseUrl+"/"+id;
    }

    @Test
    public void testCreateCinema() throws Exception {

        Cinema cinema = new Cinema();
        cinema.setName("Bergakungen");

        ResponseEntity<Cinema> responseEntity = restTemplate.postForEntity(cinemaApiBaseUrl, cinema, Cinema.class);
        Cinema postedCinema = responseEntity.getBody();;

        // Spy cinema with id from result
        Cinema spyCinema = spy(cinema);
        when(spyCinema.getId()).thenReturn(postedCinema.getId());

        //Using Overriden equals method in real object
        Boolean postedAndRetrievedObjectAreEqual = postedCinema.equals(spyCinema);
        assertEquals("postedAndRetrievedObjectAreEqual expects true", true, postedAndRetrievedObjectAreEqual);


    }

    @Test
    public void testReadCinema() throws Exception {
        //Get
        ResponseEntity<Cinema> responseEntity = restTemplate.getForEntity(urlWithId,Cinema.class);
        Cinema cinema = responseEntity.getBody();

        //Assert
        assertEquals(id,cinema.getId());


    }

    @Test
    public void testReadAllMovies() throws Exception {

    }

    @Test
    public void testUpdateCinema() throws Exception {
        ResponseEntity<Cinema> responseEntity = restTemplate.getForEntity(urlWithId,Cinema.class);
        Cinema toUpdate = responseEntity.getBody();

        toUpdate.setName("Hagabion");

        restTemplate.put(urlWithId,toUpdate);

        responseEntity = restTemplate.getForEntity(urlWithId, Cinema.class);
        Cinema updatedCinema = responseEntity.getBody();

        //Assert
        assertEquals("input same as result",toUpdate,updatedCinema);

    }

    @Test(expected = HttpServerErrorException.class)
    public void testDeleteCinema() throws Exception {
        //Delete object
        restTemplate.delete(urlWithId, Cinema.class);

        restTemplate.getForEntity(urlWithId, Cinema.class);

    }
}