package filmr.controllers;

/**
 * Created by Adrian och Erik on 2016-04-29.
 */

import filmr.Application;
import filmr.domain.Cinema;
import filmr.domain.Theater;
import filmr.repositories.CinemaRepository;
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

import static junit.framework.TestCase.assertEquals;
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

public class TheaterControllerTest {

    //Used instead of SpringJunit4ClassRunner in @RunWith
    private TestContextManager testContextManager;
    //Variables
    private MediaType jsonContentType;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
	private TheaterRepository theaterRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    private String baseUrl;
    //Variables for testing values
    private int tableSize;
    private Theater savedTheater;
    private Cinema savedCinema;

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

    public TheaterControllerTest(Long id) {
        this.id = id;
        jsonContentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(),
                Charset.forName("utf8"));
        baseUrl = "/api/theaters/";
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
        theaterRepository.deleteAllInBatch();
        cinemaRepository.deleteAllInBatch();
        //TODO on read test, make sure data.sql is not read

        //Create cinema and theater with parameters
        Cinema cinema = EntityFactory.createCinema("Global Test Cinema");
        savedCinema = cinemaRepository.save(cinema);
        Theater theater = EntityFactory.createTheater("Global Test Theater", savedCinema);
        savedTheater = theaterRepository.save(theater);

        tableSize = theaterRepository.findAll().size();
    }

    @Test
    public void testCreate() throws Exception {
        Theater theater = EntityFactory.createTheater("testCreate Theater", savedCinema);

        String jsonObject = getAsJsonString(theater);

         mockMvc.perform(post(baseUrl)
                .contentType(jsonContentType)
                .content(jsonObject)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType))
                .andExpect(jsonPath("$.name", is(theater.getName())));
        assertEquals("Amount of theaters should be +1", tableSize +1, theaterRepository.findAll().size());
    }

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
