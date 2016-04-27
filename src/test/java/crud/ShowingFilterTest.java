package crud;

import filmr.Application;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by luffarvante on 2016-04-27.
 */
public class ShowingFilterTest {

    //Variables
    private static ConfigurableApplicationContext applicationContext;
    private RestTemplate restTemplate;
    private String baseUrl;

    @BeforeClass
	public static void testClassSetup(){
		applicationContext = SpringApplication.run(Application.class, new String[0]);
	}

    @Before
	public void setup() {
		restTemplate = new RestTemplate();
        baseUrl = "http://localhost:8080/filmr/api/showings";
	}

    @Test
    public void testFilteredShowing() {

        //List<Showing> expectedEmptyList = new ArrayList<Showing>();

        HashMap<String, String> paramsoo = new HashMap<>();
        paramsoo.put("from_date", "");
        paramsoo.put("to_date", "");
        paramsoo.put("mininum_available_tickets", "");
        paramsoo.put("only_for_movie_with_id", "");
        paramsoo.put("limit", "");
 		//List<Showing> actualListFromEmptyDatabase = (List<Showing>) restTemplate.getForObject(baseUrl, List.class);
        ResponseEntity<Object[]> bob = restTemplate.getForEntity(baseUrl, Object[].class, paramsoo);
 		//assertEquals(expectedEmptyList, actualListFromEmptyDatabase);
    }
}
