package filmr.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Entity testing is kind of stupid, this is just to display some basic understanding of mocking as it was
 * never used in the "real" tests
 */
@RunWith(Parameterized.class)
public class MovieTest {

    private Movie movie;
    private String movieTitle;
    private String movieDescription;
    private Long movieLength;
    private Long movieId;

    @Parameterized.Parameters
    public static Collection<Object[]> movieTitles() {
        return Arrays.asList(new Object[][]{
                {"Lion Dude", "A movie about a dude that is a lion.", new Long(120), new Long(1)},
                {"Facist Utopia", "A movie about bush and hitler getting it on.", new Long(120), new Long(2)},
                {"Flame Wars", "A 4chan documentary.", new Long(120), new Long(3)},
                {"Hunter Ghosts", "Ghosts hunting deer.", new Long(120), new Long(4)},
                {"FBI Killers", "FBI agent serial killers hunted by FBI.", new Long(120), new Long(5)},
        });
    }

    public MovieTest(String movieTitle, String movieDescription, Long movieLength, Long movieId) {
        movie = Mockito.mock(Movie.class);
        this.movieTitle = movieTitle;
        this.movieDescription = movieDescription;
        this.movieLength = movieLength;
        this.movieId = movieId;
    }

    @Test
    public void testGetTitle() {

        LocalDateTime l = LocalDateTime.now();
        System.out.println(l);

        when(movie.getTitle()).thenReturn(movieTitle);

        assertEquals("test if getTitle in movie returns correct String", movieTitle, movie.getTitle());
    }

    @Test
    public void testEquals() {
        Movie m = new Movie();
        Movie mm = new Movie();

        m.setDescription("1");
        mm.setDescription("1");
        m.setTitle("2");
        mm.setTitle("2");
        m.setLengthInMinutes(new Long(3));
        mm.setLengthInMinutes(new Long(3));

        assertEquals(m, mm);
    }

    @Test
    public void testSetTitle() {
        movie.setTitle(movieTitle);
        verify(movie).setTitle(Matchers.eq(movieTitle));
        when(movie.getTitle()).thenReturn(movieTitle);
        assertEquals(movieTitle, movie.getTitle());
    }

    @Test
    public void testGetDescription() {
        when(movie.getDescription()).thenReturn(movieDescription);
        assertEquals("test if getDescription returns correct String", movieDescription, movie.getDescription());
    }

    @Test
    public void testGetLenghtInMinutes() {
        when(movie.getLengthInMinutes()).thenReturn(movieLength);
        assertEquals("test if getLengthInMinutes returns proper Long", movieLength, movie.getLengthInMinutes());
    }

    @Test
    public void testGetId() {
        when(movie.getId()).thenReturn(movieId);
        assertEquals("tests if getId() returns proper id", movieId, movie.getId());
    }
}
