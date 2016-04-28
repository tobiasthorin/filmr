package filmr.springboottest;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.JsonMappingException;

import filmr.Application;
import filmr.domain.Movie;
import filmr.repositories.MovieRepository;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles({"test"})
public class TestsTheSpringBootWayQM {
	
	
	private MediaType jsonContentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
	
	
	private MockMvc mockMvc;
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@Autowired
	private MovieRepository movieRepo;
	
	private String movieBaseUrl = "/api/movies/";
	private Movie savedMovie;
	
	
	@Before
	public void clearTablesAndCreateOneMovie(){
		// this can't be done in a static @BeforeClass. 
		if(this.mockMvc == null) {
			this.mockMvc = webAppContextSetup(webApplicationContext).build();
		}
		
		// clear all movies first - start fresh
		movieRepo.deleteAllInBatch();
		
		// create test movie
		Movie m1 = new Movie();
		m1.setTitle("Lion King");
		m1.setDescription("Story about..");
		m1.setLengthInMinutes(new Long(124));
		
		// save it. so we can get it in the test below
		savedMovie = movieRepo.save(m1);
		
		System.out.println(savedMovie);
		System.out.println("size is: " + movieRepo.findAll().size());
		
	}
	
	@Test
	public void testReadOne() throws Exception {
		
		// each .andExpect  is like an assert, so each can give a "expected x, got y"-error.
		mockMvc.perform(get(movieBaseUrl + savedMovie.getId()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(jsonContentType))
			.andExpect(jsonPath("$.title", is(savedMovie.getTitle())))
			.andExpect(jsonPath("$.description", is(savedMovie.getDescription())))
			// .intValue() is needed. unclear why. Json has no Longs?
			.andExpect(jsonPath("$.lengthInMinutes", is(savedMovie.getLengthInMinutes().intValue())));
			
	}
	
	@Test
	public void testUpdateOne() throws Exception {
		
		String newTitle = "Lion cat";
		savedMovie.setTitle(newTitle);
		String jsonMovie = json(savedMovie);
		
		mockMvc.perform(
				put(movieBaseUrl + savedMovie.getId())
				.contentType(jsonContentType)
				.content(jsonMovie)
				)
			.andExpect(status().isOk())
			.andExpect(content().contentType(jsonContentType))
			.andExpect(jsonPath("$.title", is(newTitle)));
			
	}
	
	
	
//	@Test(expected = JsonMappingException.class)
//	public void testDeleteOne() throws Exception {
//		
//		
//		// each .andExpect  is like an assert, so each can give a "expected x, got y"-error.
//		mockMvc.perform(delete(movieBaseUrl + savedMovie.getId()))
//			.andExpect(status().isOk());
//		
//		mockMvc.perform(get(movieBaseUrl + savedMovie.getId()))
//		.andExpect(status().isInternalServerError());
//
//	}
	
	
	
	
	// ugly methods that help everything else look nice. (along with static imports of cool methods)
	
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
