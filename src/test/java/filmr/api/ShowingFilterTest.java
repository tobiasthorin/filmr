package filmr.api;

import filmr.Application;
import filmr.domain.*;
import filmr.repositories.*;
import filmr.testfactories.EntityFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestContextManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(Parameterized.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest
@ActiveProfiles({"test"})
public class ShowingFilterTest {

    //Used instead of SpringJunit4ClassRunner in @RunWith
    private TestContextManager testContextManager;
    //Variables
    @Autowired
    private ShowingRepository showingRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private CinemaRepository cinemaRepository;

    private RestTemplate restTemplate;
    private String baseUrl;
    private String urlWithId;
    //Variables for testing values
    private int tableSize;
    private Showing savedShowing;
    private Movie savedMovie;
    private Theater savedTheater;
    private Cinema savedCinema;

    //Parameters for url
    private String fromDate = "from_date";
    private String toDate = "to_date";
    private String minimumAvailableTickets = "minimum_available_tickets";
    private String onlyForMovieWithId = "only_for_movie_with_id";
    private String onlyForTheaterWithId = "only_for_theater_with_id";
    private String onlyForCinemaWithId = "only_for_cinema_with_id";
    private String limit = "limit";
    private String showDisabledShowings = "show_disabled_showings";
    private String includeDistinctMoviesInHeader = "include_distinct_movies_in_header";
    private String includeEmptySlotsForMovieOfLenght = "include_empty_slots_for_movie_of_length";

    //Parameters
    private Long id;
    private String fromDateParameter;
    private String toDateParameter;
    private String availableTickets;
    private String movieIdParameter;
    private String theaterIdParameter;
    private String cinemaIdParameter;
    private String limitParameter;
    private String showDisabledParameter;
    private String includeDistinctHeaders;
    private String includeEmptySlots; //TODO test these later

    private boolean useMovie;
    private boolean useTheater;
    private boolean useCinema;


    //ID, ? TODO parameters pointless for this test
    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {"", null, "", false, false, false, "", "", "", ""},
                {"2016-05-04T14:14:04", null, "", false, false, false, "", "", "", ""},
                //{"", "2016-05-10T14:14:04", "", false, false, false, "", "", "", ""},
                {"", LocalDateTime.now().plusDays(2), "", false, false, false, "", "", "", ""},
                {"", null, "0", false, false, false, "", "", "", ""},
                {"", null, "", true, false, false, "", "", "", ""}, //TODO currently disabled
                {"", null, "", false, true, false, "", "", "", ""},
               // {"", null, "", false, false, true, "", "", "", ""}, //TODO disabled, se further down for info
                {"", null, "", false, false, false, "10", "", "", ""},
                {"", null, "", false, false, false, "", "true", "", ""},
               // {"", null, "", false, false, false, "", "", "", ""}, //TODO disabled until time to test these filter options
               // {"", null, "", false, false, false, "", "", "", ""}, //end single
        });
    }

    public ShowingFilterTest(String fromDateParameter, LocalDateTime toDateParameter, String availableTicketsParameter, boolean useMovie, boolean useTheater, boolean useCinema, String limitParameter, String showDisabledParameter, String inludeDistinctHeadersParameter, String includeEmptySlotsParameter) {
        baseUrl = "http://localhost:8080/filmr/api/showings/";
        this.fromDateParameter = fromDateParameter;
       // this.toDateParameter = toDateParameter;
        //TODO ugly fix
        if (toDateParameter != null) {
            this.toDateParameter = toDateParameter.toString();
            this.toDateParameter = this.toDateParameter.substring(0, this.toDateParameter.length()-4);
        }
        else
            this.toDateParameter = "";
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("parsed date: "+this.toDateParameter);
        this.availableTickets = availableTicketsParameter;
        this.useMovie = useMovie;
        this.useTheater = useTheater;
        this.useCinema = useCinema;
        this.limitParameter = limitParameter;
        this.showDisabledParameter = showDisabledParameter;
        this.includeDistinctHeaders = inludeDistinctHeadersParameter;
        this.includeEmptySlots = includeEmptySlotsParameter;
    }

    @Before
    public void resetDatabase() throws Exception {
        //Initialize replacement for SpringJunit4ClassRunner
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);

        //Initialize restTemplate
        restTemplate = new RestTemplate();

        //clear everything
        showingRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();
        theaterRepository.deleteAllInBatch();
        cinemaRepository.deleteAllInBatch();

        //Create showing and everything that belongs in it
        Movie movie = EntityFactory.createMovie("Global Test Movie", "A Movie About Cows Murdering cute bunnies", new Long(120), new Double(100));
        savedMovie = movieRepository.save(movie);
        Cinema cinema = EntityFactory.createCinema("Global Test Cinema");
        savedCinema = cinemaRepository.save(cinema);
        Theater theater = EntityFactory.createTheater("Global Test Theater", savedCinema);
        savedTheater = theaterRepository.save(theater);
        Showing showing = EntityFactory.createShowing(LocalDateTime.now().plusDays(1), savedMovie, savedTheater, new ArrayList<>());

        savedShowing = showingRepository.save(showing);

        //Setup id for this run
        id = savedShowing.getId();
        urlWithId = baseUrl+id;

        if (useMovie) {
            movieIdParameter = savedMovie.getId().toString();
        } else {
            movieIdParameter = "";
        }
        if (useTheater) {
            theaterIdParameter = savedTheater.getId().toString();
        } else {
            theaterIdParameter = "";
        }
        if (useCinema) {
            cinemaIdParameter = savedCinema.getId().toString();
        } else {
            cinemaIdParameter = "";
        }

        tableSize = showingRepository.findAll().size();
        System.out.println("Table size: " + tableSize);
    }

    @Test //2016-05-05T15:07:15.000Z
    public void testFilterNoParameters() {

        HashMap<String, String> params = getFilterParameters(fromDateParameter, toDateParameter, availableTickets, movieIdParameter, theaterIdParameter, cinemaIdParameter, limitParameter, showDisabledParameter, "", "");
        URI uri = getURI(params);
        ResponseEntity<Showing[]> responseEntity =restTemplate.getForEntity(uri, Showing[].class);
        Showing[] showings = responseEntity.getBody();

        HttpHeaders h = responseEntity.getHeaders(); //TODO use later

        LocalDateTime compareDate;

        for (Showing showing : showings) {
            if (!params.get(fromDate).equals("")) {
                //all returned showings should be after fromDate
                compareDate = LocalDateTime.parse(fromDateParameter+".000");
                assertTrue("The date should be after fromDate ", showing.getShowDateTime().isAfter(compareDate));
            }
            if (!params.get(toDate).equals("")) {
                //all returned showings should be before or up to toDate
                compareDate = LocalDateTime.parse(toDateParameter+".000");
                assertTrue("The date should be before or equal to toDate", showing.getShowDateTime().isBefore(compareDate) || showing.getShowDateTime().isEqual(compareDate));
            }
            if (!params.get(minimumAvailableTickets).equals("")) {
                Long availTicks = Long.parseLong(availableTickets);
                assertTrue("Make sure there are at least as many available tickets as requested", availTicks <= showing.getBookings().size()); //TODO wait for backend
            }
            if (!params.get(onlyForMovieWithId).equals("")) {
                assertEquals("Assert movie id is equal to showing movie id", savedMovie.getId(), showing.getMovie().getId());
            }
            if (!params.get(onlyForTheaterWithId).equals("")) {
                assertEquals("Assert theater id is equal to showing theater id", savedTheater.getId(), showing.getTheater().getId());
            }
            if (!params.get(onlyForCinemaWithId).equals("")) {
                assertEquals("Assert cinema id is equal to showing cinema id", savedCinema.getId(), showing.getTheater().getCinema().getId()); //TODO nullpointer, annotations wont let me
            }
            if (!params.get(limit).equals("")) {
                Long limit = Long.parseLong(limitParameter);
                assertTrue("Assert amount did not surpass limit", showings.length <= limit);
            }
            if (!params.get(showDisabledShowings).equals("")) {
                Boolean showDisabled = Boolean.parseBoolean(showDisabledParameter);
                assertEquals("Make sure show disabled filter works right", showDisabled, !showing.getIsDisabled());
            }
            else {
                assertEquals("Disabled shouldnt be showed", false, showing.getIsDisabled());
            }
        }
    }

    private URI getURI (HashMap<String, String> params) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam(fromDate, params.get(fromDate))
                .queryParam(toDate, params.get(toDate))
                .queryParam(minimumAvailableTickets, params.get(minimumAvailableTickets))
                .queryParam(onlyForMovieWithId, params.get(onlyForMovieWithId))
                .queryParam(onlyForTheaterWithId, params.get(onlyForTheaterWithId))
                .queryParam(onlyForCinemaWithId, params.get(onlyForCinemaWithId))
                .queryParam(limit, params.get(limit))
                .queryParam(showDisabledShowings, params.get(showDisabledShowings))
                .queryParam(includeDistinctMoviesInHeader, params.get(includeDistinctMoviesInHeader))
                .queryParam(includeEmptySlotsForMovieOfLenght, params.get(includeEmptySlotsForMovieOfLenght));

        UriComponents uriComponents = uriComponentsBuilder.build();
        return uriComponents.toUri();
    }
    private HashMap<String, String> getFilterParameters(String fDate, String tDate, String minTickets, String movieId, String theaterId, String cinemaId, String limiter, String showDisabled, String includeMovies, String includeEmptySlots) {
        HashMap<String, String> params = new HashMap<>();
        params.put(fromDate, fDate);
        params.put(toDate, tDate);
        params.put(minimumAvailableTickets, minTickets);
        params.put(onlyForMovieWithId, movieId);
        params.put(onlyForTheaterWithId, theaterId);
        params.put(onlyForCinemaWithId,cinemaId);
        params.put(limit, limiter);
        params.put(showDisabledShowings, showDisabled);
        params.put(includeDistinctMoviesInHeader, includeMovies);
        params.put(includeEmptySlotsForMovieOfLenght, includeEmptySlots);

        return params;
    }
}
