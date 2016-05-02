package filmr.controllers;

/**
 * Created by Adrian and Erik on 2016-04-29.
 */

import filmr.Application;
import filmr.domain.Movie;
import filmr.domain.Repertoire;
import filmr.domain.Theater;
import filmr.repositories.MovieRepository;
import filmr.repositories.RepertoireRepository;
import filmr.testfactories.EntityFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(Parameterized.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles({"test"})

public class RepertoireControllerTest {

    //Used instead of SpringJunit4ClassRunner in @RunWith
    private TestContextManager testContextManager;
    //Variables
    private MediaType jsonContentType;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
	private RepertoireRepository repertoireRepository;
    @Autowired
    private MovieRepository movieRepository;
    private String baseUrl;
    //Variables for testing values
    private int tableSize;
    private Repertoire savedRepertoire;
    private Movie savedMovie;

    //Mock clone of project
    private MockMvc mockMvc;

    //Parameters
    private Long id;

    //ID, ?
    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {new Long(1)},
        });
    }

    public RepertoireControllerTest(Long id) {
        this.id = id;
        jsonContentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(),
                Charset.forName("utf8"));
        baseUrl = "/api/repertoires/";
    }

    @Before
	public void resetDatabase() throws Exception {
        //Initialize replacement for SpringJunit4ClassRunner
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);

        // this can't be done in a static @BeforeClass.
		if(this.mockMvc == null) {
			this.mockMvc = webAppContextSetup(webApplicationContext).build();
		}
        //clear everything
        repertoireRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();
        //TODO on read test, make sure data.sql is not read

        //Create repertoire with parameters
        Repertoire repertoire = EntityFactory.createRepertoire();
        savedRepertoire = repertoireRepository.save(repertoire);
        Movie movie = EntityFactory.createMovie("Global Test Movie", "A movie about pigs", new Long(120));
        savedMovie = movieRepository.save(movie);

        tableSize = repertoireRepository.findAll().size();
    }

    @Test
    public void testCreate() throws Exception {
        Repertoire repertoire = EntityFactory.createRepertoire();

        String jsonObject = getAsJsonString(repertoire);

         mockMvc.perform(post(baseUrl)
                .contentType(jsonContentType)
                .content(jsonObject)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType))
                .andExpect(jsonPath("$.movies", is(repertoire.getMovies())));
        assertEquals("Amount of repertoires should be +1", tableSize +1, repertoireRepository.findAll().size());
    }

    @Test
    public void testAddMovieToRepertoire() throws Exception {
        List<Movie> movies = savedRepertoire.getMovies();
        int oldSize = movies.size();
        movies.add(savedMovie);
        savedRepertoire.setMovies(movies);
        String jsonObject = getAsJsonString(savedRepertoire);

        ResultActions resultAction = mockMvc.perform(
                put(baseUrl + savedRepertoire.getId())
                .contentType(jsonContentType)
                .content(jsonObject)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType))
                .andExpect(jsonPath("$.movies").isArray())
                .andExpect(jsonPath("$.movies", hasSize(savedRepertoire.getMovies().size())))
            //    .andExpect(jsonPath("$.movies", contains(movies)));//TODO this should work but the item(s) are of the wrong type
        ;

        //TODO check that added movie is added to repertoire properly
        assertEquals("Amount of movies in repertoire should be +1", oldSize+1, savedRepertoire.getMovies().size());

        // pick out the response data.
        MvcResult result = resultAction.andReturn();
        String resultString = result.getResponse().getContentAsString();
        System.out.println("json is" + resultString);

        List<Movie> updatedMovieList = (List<Movie>) JsonPath.read(resultString, "$.movies");
        assertEquals("Movies in updated repertoire should be same as the ones sent to API", savedRepertoire.getMovies(), updatedMovieList);
        // TODO: figure out why the casting fails. Seems to be specific to Repertoire?
    }

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired // ???
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
				hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

		 Assert.assertNotNull("the JSON message converter must not be null",
				this.mappingJackson2HttpMessageConverter);
	}

    protected String getAsJsonString(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                    o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
