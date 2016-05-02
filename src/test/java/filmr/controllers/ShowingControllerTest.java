package filmr.controllers;

import filmr.Application;
import filmr.domain.*;
import filmr.repositories.CinemaRepository;
import filmr.repositories.MovieRepository;
import filmr.repositories.ShowingRepository;
import filmr.repositories.TheaterRepository;
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
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.*;

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
public class ShowingControllerTest {

    //Used instead of SpringJunit4ClassRunner in @RunWith
    private TestContextManager testContextManager;
    //Variables
    private MediaType jsonContentType;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
	private ShowingRepository showingRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private TheaterRepository theaterRepository;
    private String baseUrl;
    //Variables for testing values
    private int tableSize;
    private Movie savedMovie;
    private Cinema savedCinema;
    private Theater savedTheater;
    private Showing savedShowing;

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

    public ShowingControllerTest(Long id) {
        this.id = id;
        jsonContentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(),
                Charset.forName("utf8"));
        baseUrl = "/api/showings/";
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
        cinemaRepository.deleteAllInBatch();
        theaterRepository.deleteAllInBatch();
        showingRepository.deleteAllInBatch();
        //TODO on read test, make sure data.sql is not read

        //Create showing and everything that belongs in it
        LocalDateTime date = LocalDateTime.now();
        Movie movie = EntityFactory.createMovie("Global Test Movie", "A movie about things", new Long(120));
        savedMovie = movieRepository.save(movie);
        Cinema cinema = EntityFactory.createCinema("Global Test Cinema");
        savedCinema = cinemaRepository.save(cinema);
        Theater theater = EntityFactory.createTheater("Global Test Theater", cinema);
        savedTheater = theaterRepository.save(theater);
        ArrayList<Booking> bookings = new ArrayList<>();//TODO can be empty
        Showing showing = EntityFactory.createShowing(date, savedMovie, savedTheater, bookings);
        savedShowing = showingRepository.save(showing);

        tableSize = showingRepository.findAll().size();
    }

    @Test
    public void testCreate() throws Exception {
        Showing showing = EntityFactory.createShowing(LocalDateTime.now(), savedMovie, savedTheater, new ArrayList<>());

        String jsonObject = getAsJsonString(showing);

        mockMvc.perform(post(baseUrl)
                .contentType(jsonContentType)
                .content(jsonObject)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType));//TODO possibly check more in detail?
        assertEquals("Assert that amount of showings is +1", tableSize +1, showingRepository.findAll().size());
    }

    //TODO null tests?

        //TODO put in help man class
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
