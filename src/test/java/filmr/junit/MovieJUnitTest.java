package filmr.junit;

import filmr.Application;
import filmr.domain.Movie;
import filmr.services.MovieService;
import filmr.testfactories.EntityFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestContextManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@SpringApplicationConfiguration(Application.class)
//@ContextConfiguration(classes = Application.class, loader = AnnotationConfigContextLoader.class)
public class MovieJUnitTest {

    //Instead of @RunWith(SpringJUnit4ClassRunner.class)
    private TestContextManager testContextManager;

    @Autowired
    private MovieService movieService;

    private MovieService spyMovieService;

    @Before
    public void buildSpy() throws Exception {
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);
        spyMovieService = spy(movieService);
    }

    @Test
    public void testService() {
        assertEquals("class ", "class filmr.services.MovieService", movieService.getClass().toString());
    }

    @Test
    public void testGetAllMoviesNotInRepertoaire() throws Exception {
        List<Movie> allMovies = new ArrayList<>();
        Movie inRepertoire = EntityFactory.createMovie("movie in repertoirn", "a movie", new Long(110), new Double(100));
        Movie notInRepertoire = EntityFactory.createMovie("movie not in repertiore", "another movie", new Long(120), new Double(110));
        allMovies.add(inRepertoire);
        allMovies.add(notInRepertoire);

        Set<Movie> repertoire = new HashSet<>();
        repertoire.add(inRepertoire);

        when(spyMovieService.readAllEntities()).thenReturn(allMovies);
        //PowerMockito.when(spyMovieService.repertoireService.readEntity(1).getMovies()).thenReturn(repertoire);
        //PowerMockito.when(spyMovieService, "readEntity").thenReturn(repertoire);

//        List<Movie> match = spyMovieService.getAllMoviesNotInRepertoire(new Long(1));
//
//        assertEquals("check", repertoire, match);

        //assertEquals("Check movies returned by spy are the same as created list", allMovies, spyMovieService.readAllEntities());
    }
}
