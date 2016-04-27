package filmr.controllers;

import filmr.Application;
import filmr.domain.Cinema;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

/**
 * Created by Marco on 2016-04-27.
 */
public class CinemaControllerTest {

    private RestTemplate restTemplate;
    private String cinemaApiBaseUrl ="http://localhost:8080/filmr/api/cinemas";

    @BeforeClass // just do this once, for the whole test class (not for each method, like @Before)
    public static void testClassSetup(){
		/* start application so that API is running */
        SpringApplication.run(Application.class, new String[0]);

    }

    @Before
    public void setUp() throws Exception {
        restTemplate = new RestTemplate();
    }

    @Test
    public void testCreateCinema() throws Exception {

        Cinema cinema = new Cinema();

        ResponseEntity<Cinema> responseEntity = restTemplate.postForEntity(cinemaApiBaseUrl, cinema, Cinema.class);
        Cinema postedCinema = responseEntity.getBody();



    }

    @Test
    public void testReadCinema() throws Exception {

    }

    @Test
    public void testReadAllMovies() throws Exception {

    }

    @Test
    public void testUpdateCinema() throws Exception {

    }

    @Test
    public void testDeleteCinema() throws Exception {

    }
}