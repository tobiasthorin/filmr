package api;

import filmr.Application;
import filmr.domain.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by luffarvante on 2016-04-27.
 */
@RunWith(Parameterized.class)
public class ShowingCRUDTestHorribleMess {

    //Variables
     private static ConfigurableApplicationContext applicationContext;
    private RestTemplate restTemplate;
    private String baseUrl;
    private String urlWithId;

    //Parameters
    private Long id;

    @Parameterized.Parameters
    public static Collection<Object[]> ids() {
        return Arrays.asList(new Object[][]{
                {new Long(1)},
                {new Long(2)},
                {new Long(3)},
                {new Long(4)},
                {new Long(5)},
                {new Long(6)},
                {new Long(7)},
                {new Long(8)},
                {new Long(9)},
                {new Long(10)},
        });
    }

    //Take parameters from parameter list and assign to variable
    public ShowingCRUDTestHorribleMess(Long id) {
        this.id = id;
    }

    @BeforeClass
	public static void testClassSetup(){
		applicationContext = SpringApplication.run(Application.class, new String[0]);
	}

    //This is done before every method
    @Before
	public void setup() {
		restTemplate = new RestTemplate();
        baseUrl = "http://localhost:8080/filmr/api/showings";
        urlWithId = baseUrl+"/"+id;
	}

    @Test
    public void testCreate() {
        //Create and setup new Showing
        Showing showing = new Showing();

        //Showing properties
        Date date = new Date();
        Movie movie;
        Theater theater;
        ArrayList<Booking> bookings;

        //Setup Mock movie
        Long movieId = new Long(1);
        String movieTitle = "Bob the Man";
        String movieDescription = "Bob walks around town looking for icecream";
        Long movieLenght = new Long(120);

        movie = Mockito.mock(Movie.class);
        when(movie.getId()).thenReturn(movieId);
        when(movie.getTitle()).thenReturn(movieTitle);
        when(movie.getDescription()).thenReturn(movieDescription);
        when(movie.getLengthInMinutes()).thenReturn(movieLenght);

        //Setup Mock theater
        Long theaterId = new Long(1);
        String theaterName = "Sal 1";

        //Setup rows for theater
        Long seatId = new Long(1);
        Long rowId = new Long(1);
        String rowLabel = "A";

        //"Done"
        Seat theaterSeat = Mockito.mock(Seat.class);
        when(theaterSeat.getId()).thenReturn(seatId);
        when(theaterSeat.getRow()).thenReturn(null); //TODO gar runt

        //Done
        ArrayList<Seat> seatsInRow = Mockito.mock(ArrayList.class);
        when(seatsInRow.get(anyInt())).thenReturn(theaterSeat);

        //Done
        Row row = Mockito.mock(Row.class);
        when(row.getId()).thenReturn(rowId);
        when(row.getRowLabel()).thenReturn(rowLabel);
        when(row.getSeats()).thenReturn(seatsInRow);

        //Done
        ArrayList<Row> theaterRows = Mockito.mock(ArrayList.class);
        when(theaterRows.get(anyInt())).thenReturn(row);

        //"Done"
        Showing mockShowing = Mockito.mock(Showing.class);
        //TODO explode

        //Done
        ArrayList<Showing> showings = Mockito.mock(ArrayList.class);
        when(showings.get(anyInt())).thenReturn(mockShowing);

        //Done
        theater = Mockito.mock(Theater.class);
        when(theater.getId()).thenReturn(theaterId);
        when(theater.getName()).thenReturn(theaterName);
        when(theater.getRows()).thenReturn(theaterRows);
        when(theater.getShowings()).thenReturn(showings);


        //Setup Booking
        Long bookingId = new Long(1);
        String bookingRefercene = "BOB BOB BOB";
        String phoneNumber = "0736 66 66 66";
        Booking booking = Mockito.mock(Booking.class);
        when(booking.getId()).thenReturn(bookingId);
        when(booking.getBookedSeats()).thenReturn(seatsInRow); //TODO srsly im not creating another one
        when(booking.getBookingReference()).thenReturn(bookingRefercene);
        when(booking.getPhoneNumber()).thenReturn(phoneNumber);
        when(booking.getShowing()).thenReturn(mockShowing);

        //Setup bookings
        bookings = Mockito.mock(ArrayList.class);
        when(bookings.get(anyInt())).thenReturn(booking);



        //Setup acutal object
        showing.setShowDateTime(date);
        showing.setMovie(movie);
        showing.setTheater(theater);
        showing.setBookings(bookings);

        //Post
        ResponseEntity<Showing> responseEntity = restTemplate.postForEntity(baseUrl, showing, Showing.class);
        Showing postedShowing = responseEntity.getBody();
    }

    @Test
    public void testRead() {
//        //Get
//        ResponseEntity<Movie> responseEntity = restTemplate.getForEntity(urlWithId, Movie.class);
//        Movie movie = responseEntity.getBody();
//
//        //Assert
//        assertEquals(id, movie.getId());
    }

    @Test
    public void testUpdate() {
//        //Read an object
//        ResponseEntity<Movie> responseEntity = restTemplate.getForEntity(urlWithId, Movie.class);
//        Movie readMovie = responseEntity.getBody();
//        Movie toUpdate = responseEntity.getBody();
//
//        String changeTitleTo = "Bobs bob";
//        toUpdate.setTitle(changeTitleTo);
//
//        //Update object in database
//        restTemplate.put(urlWithId, toUpdate);
//
//        //Read object again
//        responseEntity = restTemplate.getForEntity(urlWithId, Movie.class);
//        Movie updatedMovie = responseEntity.getBody();
//
//        //Assert
//        assertNotSame("check that the original movie differs from the updated read one", readMovie, updatedMovie);
//        assertEquals("check that the updated field is correct", changeTitleTo, updatedMovie.getTitle());
    }

    @Test(expected = HttpClientErrorException.class)
    public void testUpdateNull() {
//        //Create object
//        Movie movie = new Movie();
//        //Try to put. Should throw HttpClientErrorException 400 Bad Request
//        restTemplate.put(urlWithId, movie);
    }

    @Test (expected = HttpServerErrorException.class)
    public void testDelete() {
//        //Delete object
//        restTemplate.delete(urlWithId, Movie.class);
//        //Try to read it (should not exist)
//        restTemplate.getForEntity(urlWithId, Movie.class);
    }
}
