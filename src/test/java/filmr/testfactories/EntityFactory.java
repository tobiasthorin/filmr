package filmr.testfactories;

import filmr.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by luffarvante on 2016-04-29.
 */
public class EntityFactory {

    public static Cinema createCinema(String name) {
        //Create cinema with parameters
        Cinema cinema = new Cinema();
        cinema.setName(name);
        //Repetoaire shouldn be set here
        ArrayList<Theater> theaters = new ArrayList<>();
        cinema.setTheaters(theaters);
        return cinema;
    }

    public static Theater createTheater(String name, Cinema savedCinema) {
        Theater theater = new Theater();
        String theaterName;
        ArrayList<Row> rows;
        ArrayList<Showing> showings;
        Boolean usingContinuousSeatLabeling;

        theaterName = name;

        rows = new ArrayList<>(); //TODO linked in DB?

        usingContinuousSeatLabeling = false;

        Row r = createStandardRowForTheater(theater);
        rows.add(r);


        theater.setName(theaterName);
//        theater.setDisabled(disabled);
        theater.setRows(rows);
//        theater.setCinema(cinema);
//        theater.setShowings(showings);
        theater.setUsingContinuousSeatLabeling(usingContinuousSeatLabeling);


        return theater;
    }

    public static Repertoire createRepertoire() {
        Repertoire repertoire = new Repertoire();
//        ArrayList<Movie> movies = null;
//        repertoire.setMovies(movies);
        return repertoire;
    }

    public static Movie createMovie(String title, String description, Long lenght, Double defaultPrice) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setLengthInMinutes(lenght);
        movie.setDefaultPrice(defaultPrice);
        return movie;
    }

    public static Showing createShowing(LocalDateTime showDateTime, Movie savedMovie, Theater savedTheater, List<Booking> savedBookings) {
        Showing showing = new Showing();
        showing.setShowDateTime(showDateTime);
        showing.setMovie(savedMovie);
        showing.setTheater(savedTheater);
        //showing.setBookings(savedBookings);
        return showing;
    }
    
	public static Seat createStandardSeatForRow(Row row){
		Seat seat = new Seat();
		seat.setState(SeatState.ENABLED);
		seat.setRow(row);
		
		return seat;
	}
	
	public static Row createStandardRowForTheater(Theater theater) {
		Row row = new Row();
		row.setSeats(new ArrayList<Seat>() );
		row.setTheater(theater);
//        Seat seat = createStandardSeatForRow(row);
//		List<Seat> seats = new ArrayList<>();
//        seats.add(seat);
//        row.setSeats(seats);
		return row;
	}

    public static Booking createBooking(Showing showing) {
        Booking booking = new Booking();
        ArrayList<Seat> bookedSeats = new ArrayList<>();
        Seat seat = showing.getTheater().getRows().get(0).getSeats().get(0);
        bookedSeats.add(seat);
        String phoneNumber = "0700000000";
        booking.setBookedSeats(bookedSeats);
        booking.setShowing(showing);
        booking.setPhoneNumber(phoneNumber);
        return booking;
    }
}
