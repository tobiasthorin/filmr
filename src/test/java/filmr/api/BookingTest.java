package filmr.api;

import filmr.Application;
import filmr.domain.*;
import filmr.repositories.*;
import filmr.testfactories.EntityFactory;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestContextManager;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

@RunWith(Parameterized.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest
@ActiveProfiles({"test"})
public class BookingTest {

    //Used instead of SpringJunit4ClassRunner in @RunWith
    private TestContextManager testContextManager;
    //Variables
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ShowingRepository showingRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private RowRepository rowRepository;
    @Autowired
    private SeatRepository seatRepository;

    private RestTemplate restTemplate;
    private String baseUrl;
    private String urlWithId;
    //Variables for testing values
    private int tableSize;
    private Showing savedShowing;
    private Showing savedExclusiveShowing;
    private Movie savedMovie;
    private Theater savedTheater;
    private Cinema savedCinema;
    private Booking savedBooking;

    //Parameters
    private Long id;

    //ID, ? TODO parameters pointless for this test
    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {new Long(1)},
        });
    }

    public BookingTest(Long id) {
        baseUrl = "http://localhost:8080/filmr/api/bookings/";
    }

    @Before
    public void resetDatabase() throws Exception {
        //Initialize replacement for SpringJunit4ClassRunner
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);

        //Initialize restTemplate
        restTemplate = new RestTemplate();

        //clear everything
        bookingRepository.deleteAllInBatch();
        seatRepository.deleteAllInBatch();
        rowRepository.deleteAllInBatch();
        showingRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();
        theaterRepository.deleteAllInBatch();
        cinemaRepository.deleteAllInBatch();

        //Create booking and everything that belongs in it
        Movie movie = EntityFactory.createMovie("Global Test Movie", "A Movie About Cows Murdering cute bunnies", new Long(120), new Double(100));
        savedMovie = movieRepository.save(movie);
        Cinema cinema = EntityFactory.createCinema("Global Test Cinema");
        savedCinema = cinemaRepository.save(cinema);
        Theater theater = EntityFactory.createTheater("Global Test Theater", savedCinema);

        //TODO factory
        Seat seat = new Seat();
        seat.setRow(theater.getRows().get(0));
        seat.setSeatLabel("Label");
        seat.setState(SeatState.ENABLED);
        theater.getRows().get(0).getSeats().add(seat);

        rowRepository.save(theater.getRows());
        seatRepository.save(seat);

        savedTheater = theaterRepository.save(theater);


        Showing showing = EntityFactory.createShowing(LocalDateTime.now().plusDays(1), savedMovie, savedTheater, new ArrayList<>());
        Showing exclusiveShowing = EntityFactory.createShowing(LocalDateTime.now().plusDays(2), savedMovie, savedTheater, new ArrayList<>());
        savedShowing = showingRepository.save(showing);
        savedExclusiveShowing = showingRepository.save(exclusiveShowing);
        Booking booking = EntityFactory.createBooking(savedShowing);
        savedBooking = bookingRepository.save(booking);

        //Setup id for this run
        id = savedBooking.getId();
        urlWithId = baseUrl+id;

        tableSize = bookingRepository.findAll().size();
    }

    @Test
    public void testCreate() throws Exception {
        Booking booking = EntityFactory.createBooking(savedExclusiveShowing);

        Long id = savedExclusiveShowing.getId();

        ResponseEntity<Booking> responseEntity = restTemplate.postForEntity(baseUrl+"?for_showing_with_id="+id, booking, Booking.class);
        Booking postedBooking = responseEntity.getBody();

        //Assert
        assertTrue("Make sure the http was successfull", responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("Compare seats", booking.getBookedSeats(), postedBooking.getBookedSeats()); //TODO seats equals method
        assertEquals("Compare showing", booking.getShowing(), postedBooking.getShowing());
        assertEquals("Compare phone numbers", booking.getPhoneNumber(), booking.getPhoneNumber());
    }

    @Test (expected = HttpClientErrorException.class)
    public void testCreateOnAlreadyBooked() {
        Booking booking = EntityFactory.createBooking(savedShowing);

        Long id = savedShowing.getId();

        ResponseEntity<Booking> responseEntity = restTemplate.postForEntity(baseUrl+"?for_showing_with_id="+id, booking, Booking.class);

        //Assert
        assertEquals("Make sure the http was unsuccessfull", HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test (expected = HttpClientErrorException.class)
    public void testCreateBookingOnShowingWithSeatOnOtherShowing() {
        Booking booking = EntityFactory.createBooking(savedShowing);
        List<Seat> seats = savedExclusiveShowing.getTheater().getRows().get(0).getSeats();
        booking.setBookedSeats(seats);

        Long id = savedShowing.getId();

        ResponseEntity<Booking> responseEntity = restTemplate.postForEntity(baseUrl+"?for_showing_with_id="+id, booking, Booking.class);

        //Assert
        assertTrue("Make sure we get the right error code", responseEntity.getStatusCode().is4xxClientError());
    }

    @Test
    public void testRead() {
        ResponseEntity<Booking> responseEntity = restTemplate.getForEntity(urlWithId, Booking.class);
        Booking booking = responseEntity.getBody();

        assertEquals("Make sure http was successfull", HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Make sure the read object is right", savedBooking, booking);
    }

    @After
    public void clearDatabase() {
        bookingRepository.deleteAllInBatch();
        seatRepository.deleteAllInBatch();
        rowRepository.deleteAllInBatch();
        showingRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();
        theaterRepository.deleteAllInBatch();
        cinemaRepository.deleteAllInBatch();
    }
}
