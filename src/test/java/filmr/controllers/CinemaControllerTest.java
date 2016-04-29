package filmr.controllers;

import filmr.Application;
import filmr.domain.Cinema;
import filmr.domain.Repertoire;
import filmr.domain.Theater;
import filmr.repositories.CinemaRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles({"test"})
public class CinemaControllerTest {

    //Variables
    private MediaType jsonContentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
	private CinemaRepository cinemaRepository;
    private String baseUrl = "/api/cinemas/";

    //Mock clone of project
    private MockMvc mockMvc;

    int size;

    @Before
	public void resetDatabase(){
        // this can't be done in a static @BeforeClass.
		if(this.mockMvc == null) {
			this.mockMvc = webAppContextSetup(webApplicationContext).build();
		}
        //clear everything
        cinemaRepository.deleteAllInBatch();
        //TODO on read test, make sure data.sql is not read

        //TODO on further testing, we may need a test cinema here
        size = cinemaRepository.findAll().size();
    }

    @Test
    public void testCreate() throws Exception {
        //Create cinema with parameters
        Cinema cinema = new Cinema();
        cinema.setName("Bio Bio");
        //Repetoaire shouldn be set here
        ArrayList<Theater> theaters = new ArrayList<>();
        cinema.setTheaters(theaters);

        //Convert Cinema to JSON string
        String jsonObject = json(cinema);

        mockMvc.perform(post(baseUrl)
                .contentType(jsonContentType)
                .content(jsonObject)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType));
        assertEquals("Amount of cinemas should be +1", size+1, cinemaRepository.findAll().size());
    }

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired // ???
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
				hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

		 Assert.assertNotNull("the JSON message converter must not be null",
				this.mappingJackson2HttpMessageConverter);
	}

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                    o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
