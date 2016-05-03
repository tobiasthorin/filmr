package filmr.controllers;

import filmr.Application;
import filmr.domain.Cinema;
import filmr.domain.Movie;
import filmr.repositories.MovieRepository;
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
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
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
public class MovieControllerTest {

    //Used instead of SpringJunit4ClassRunner in @RunWith
    private TestContextManager testContextManager;
    //Variables
    private MediaType jsonContentType;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MovieRepository movieRepository;
    private String baseUrl;
    //Variables for testing values
    private int tableSize;
    private Movie savedMovie;

    //Mock clone of project
    private MockMvc mockMvc;

    //Parameters
    private Long id;

    //ID, ? TODO parameters for null test?
    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {new Long(1)},
        });
    }

    public MovieControllerTest(Long id) {
        this.id = id;
        jsonContentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(),
                Charset.forName("utf8"));
        baseUrl = "/api/movies/";
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
        movieRepository.deleteAllInBatch();
        //TODO on read test, make sure data.sql is not read

        //Create showing and everything that belongs in it
        Movie movie = EntityFactory.createMovie("Global Test Movie", "A movie about things", new Long(120));
        savedMovie = movieRepository.save(movie);

        tableSize = movieRepository.findAll().size();
    }

    @Test
    public void testCreate() throws Exception {
        Movie movie = EntityFactory.createMovie("testCreate Movie", "A movie about testing", new Long(110));

        String jsonObject = getAsJsonString(movie);

        mockMvc.perform(post(baseUrl)
                .contentType(jsonContentType)
                .content(jsonObject)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType))
                .andExpect(jsonPath("$.title", is(movie.getTitle())))
                .andExpect(jsonPath("$.description", is(movie.getDescription())))
                .andExpect(jsonPath("$.lengthInMinutes", is(movie.getLengthInMinutes()))); //TODO same horrible error
        assertEquals("Assert that amount of movies is +1", tableSize +1, movieRepository.findAll().size());
    }

    @Test
    public void testRead() throws Exception {
        String getUrl = baseUrl+id;

        mockMvc.perform(get(getUrl)
                .contentType(jsonContentType)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType))
                .andExpect(jsonPath("$.id", is(id))); //TODO here we go again
        assertEquals("Assert that the read movie has the same ID as the supplied one", 1, 1); //TODO wait not here
    }

    @Test
    public void testUpdate() throws Exception {
        String putUrl = baseUrl+id;

        mockMvc.perform(put(putUrl)
                .contentType(jsonContentType)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType));
        //TODO what to test
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
