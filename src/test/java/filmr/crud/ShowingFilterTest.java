package filmr.crud;

import filmr.Application;
import filmr.domain.Showing;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Adrian on 2016-04-27.
 */

@RunWith(Parameterized.class)
public class ShowingFilterTest {

    //Variables
    private static ConfigurableApplicationContext applicationContext;
    private RestTemplate restTemplate;
    private String baseUrl;

    //Parameters
    private String fDate;
    private String tDate;
    private String minTickets;
    private String movieId;
    private String theaterId;
    private String limit;
    private String showDisabled;

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                //from_date, to_date, min tickets, movie id, theater id, limit, show disabled
                {"", "", "", "", "", "", ""}, //get all
                {"2016-04-20 12:00", "", "", "", "", "", ""}, //get all from date
                {"", "2016-04-30 23:59", "", "", "", "", ""},//get all before and up to date
                {"2016-04-27 12:00", "2016-04-30 23:59", "", "", "", "", ""},//get all from date up to date
                {"", "", "", "1", "", "", ""},//get all movies with id
                {"", "", "", "", "1", "", ""},//get all from theater
                {"", "", "", "", "", "", "true"},//display disabled
                {"", "", "", "", "", "5", ""},//limit
        });
    }

    public ShowingFilterTest(String fDate, String tDate, String minTickets, String movieId, String theaterId, String limit, String showDisabled) {
        this.fDate = fDate;
        this.tDate = tDate;
        this.minTickets = minTickets;
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.limit = limit;
        this.showDisabled = showDisabled;
    }

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
        String fromDate = "from_date";
        String toDate = "to_date";
        String minimumAvailableTickets = "minium_available_tickets";
        String onlyForMovieWithId = "only_for_movie_with_id";
        String onlyForTheaterWithId = "only_for_theater_with_id";
        String limit = "limit";
        String showDisabledShowings = "show_disabled_showings";


        HashMap<String, String> params = new HashMap<>();
        params.put(fromDate, fDate);
        params.put(toDate, tDate);
        params.put(minimumAvailableTickets, minTickets);
        params.put(onlyForMovieWithId, movieId);
        params.put(onlyForTheaterWithId, theaterId);
        params.put(limit, this.limit);
        params.put(showDisabledShowings, showDisabled);

        UriComponentsBuilder b = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam(fromDate, params.get(fromDate))
                .queryParam(toDate, params.get(toDate))
                .queryParam(minimumAvailableTickets, params.get(minimumAvailableTickets))
                .queryParam(onlyForMovieWithId, params.get(onlyForMovieWithId))
                .queryParam(onlyForTheaterWithId, params.get(onlyForTheaterWithId))
                .queryParam(limit, params.get(limit))
                .queryParam(showDisabledShowings, params.get(showDisabledShowings));

        UriComponents u = b.build();

        ResponseEntity<Showing[]> r =restTemplate.getForEntity(u.toUri(), Showing[].class);

        Showing[] s = r.getBody();

        for(Showing ss : s) {
            System.out.println(ss.getMovie().getTitle()+" "+ss.getMovie().getId()+" shown in theater "+ss.getTheater().getName()+" "+ss.getTheater().getId());
            System.out.println(" isDisabled: "+ss.getIsDisabled());
            System.out.println(" date: "+ss.getShowDateTime());

//            if (!params.get(fromDate).equals("")) {
//                System.out.println("  should be after: "+params.get(fromDate));
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                Date paramDate = null;
//                try {
//                    paramDate = dateFormat.parse(params.get(fromDate));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                int compare = ss.getShowDateTime().compareTo(paramDate); //TODO should be after or exactly same time; +1 or 0
//                boolean after = compare > -1; //TODO may break
//                assertTrue("check that from_date is correct", after);
//            }
//
//            if (!params.get(toDate).equals("")) {
//                System.out.println("  should be before: "+params.get(toDate));
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                Date paramDate = null;
//                try {
//                    paramDate = dateFormat.parse(params.get(toDate));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                int compare = ss.getShowDateTime().compareTo(paramDate); //TODO should be before -1 or 0
//                boolean before = compare < 1;
//                System.out.println("     compared dates: "+ss.getShowDateTime()+" : "+paramDate+" difference: "+compare+" result: "+before);
//                assertTrue("check that to_date is correct", before);
//            }

            if (!params.get(minimumAvailableTickets).equals("")) {
                assertEquals("check that there is the right amount of available tickets", params.get(minimumAvailableTickets), ss.getBookings().size()); //TODO wait for backend
            }
            if (!params.get(onlyForMovieWithId).equals("")) {
                assertEquals("check that id is correct", params.get(onlyForMovieWithId), ss.getMovie().getId().toString());
            }
            if (!params.get(onlyForTheaterWithId).equals("")) {
                assertEquals("check theater", params.get(onlyForTheaterWithId), ss.getTheater().getId().toString());
            }
            if (!params.get(limit).equals("")) {
                boolean limitUpheld = s.length <= Integer.parseInt(this.limit);
                assertTrue("check that limit is upheld", limitUpheld);
            }
            if (!params.get(showDisabledShowings).equals("true")) {
                boolean disabled = ss.getIsDisabled();
                assertTrue("check that its not actually disabled", !disabled);
            }
        }


    }
}
