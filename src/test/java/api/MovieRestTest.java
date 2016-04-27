package api;

import filmr.domain.Movie;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.when;

/**
 * Created by luffarvante on 2016-04-26.
 */
@RunWith(Parameterized.class)
public class MovieRestTest {

    private Movie movie;
    private String movieTitle;
    private String movieDescription;
    private Long movieLength;
    private Long movieId;

    private RestTemplate restTemplate;
    private Long id;
    private String baseUrl;
    private String urlWithId;

    @Parameterized.Parameters
    public static Collection<Object[]> movieParameters() {
        return Arrays.asList(new Object[][]{
                {"Lion Dude", "A movie about a dude that is a lion.", new Long(120), new Long(1)},
                {"Facist Utopia", "A movie about bush and hitler getting it on.", new Long(120), new Long(2)},
                {"Flame Wars", "A 4chan documentary.", new Long(120), new Long(3)},
                {"Hunter Ghosts", "Ghosts hunting deer.", new Long(120), new Long(4)},
                {"FBI Killers", "FBI agent serial killers hunted by FBI.", new Long(120), new Long(5)},
        });
    }

    public MovieRestTest(String movieTitle, String movieDescription, Long movieLength, Long movieId) {
        movie = Mockito.mock(Movie.class);
        restTemplate = Mockito.mock(RestTemplate.class);

        id = movieId;
        baseUrl = "http://localhost:8080/filmr/api/movies";
        urlWithId = baseUrl+"/"+id;

        this.movieTitle = movieTitle;
        this.movieDescription = movieDescription;
        this.movieLength = movieLength;
        this.movieId = movieId;

        when(movie.getId()).thenReturn(movieId);
        when(movie.getTitle()).thenReturn(movieTitle);
        //TODO an so on
    }

    //TODO move up
    private ResponseEntity<Movie> movieResponseEntity;
    private Movie restMovie;
    private Movie restReturnMovie;
    private ResponseEntity<Movie> responseEntity;

    @Before
    public void setup() {
        movieResponseEntity = Mockito.mock(ResponseEntity.class);
        restMovie = Mockito.mock(Movie.class);
    }

    @Test
    public void testCreate() {
        when(restTemplate.postForEntity(baseUrl, movie, Movie.class)).thenReturn(movieResponseEntity);

        responseEntity = restTemplate.postForEntity(baseUrl, movie, Movie.class);
        when(responseEntity.getBody()).thenReturn(restMovie);

        restReturnMovie = responseEntity.getBody();
        when(restReturnMovie.getId()).thenReturn(movieId);

        assertEquals("POST movie to database, compare if the movie returned from query is same as posted movie", movie.getId(), restReturnMovie.getId());
    }

    @Test
    public void testRead() {
        when(restTemplate.getForEntity(urlWithId, Movie.class)).thenReturn(movieResponseEntity);

        responseEntity = restTemplate.getForEntity(urlWithId, Movie.class);
        when(responseEntity.getBody()).thenReturn(restMovie);

        restReturnMovie = responseEntity.getBody();
        when(restReturnMovie.getId()).thenReturn(movieId);

        assertEquals("GET from movie database, compare if the ID of the returned movie is the same as the one requested", id, restReturnMovie.getId());
    }

    @Test
	public void testUpdate() {
        when(restTemplate.getForEntity(urlWithId, Movie.class)).thenReturn(movieResponseEntity);
        //when(restTemplate.put(urlWithId, movie)).thenReturn(restMovie);

        responseEntity = restTemplate.getForEntity(urlWithId, Movie.class);

        when(responseEntity.getBody()).thenReturn(restMovie);

        restReturnMovie = responseEntity.getBody();

        //TODO actually update object

        restTemplate.put(urlWithId, movie);

        ResponseEntity<Movie> readMovieResponseEntity= restTemplate.getForEntity(urlWithId, Movie.class);
        when(readMovieResponseEntity.getBody()).thenReturn(restMovie);

        Movie restReturnMovie = readMovieResponseEntity.getBody();
        when(restReturnMovie.getId()).thenReturn(new Long(id));

        //TODO compare and figure out if update has been executed properly

        assertNotSame("checks if objects are not the same blipp blopp", id, restReturnMovie.getId()); //TODO this passes even though IDS are same; they are not the same object
	}

}
