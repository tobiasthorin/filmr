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
        boolean disabled;
        ArrayList<Row> rows;
        Cinema cinema;
        ArrayList<Showing> showings;

        theaterName = name;

        disabled = false; //TODO parameter?

        rows = new ArrayList<>(); //TODO linked in DB?

        cinema = savedCinema;

        showings = new ArrayList<>(); //TODO should be empty no errors?

        theater.setName(theaterName);
//        theater.setDisabled(disabled);
//        //theater.setRows(rows);
//        theater.setCinema(cinema);
//        theater.setShowings(showings);
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
    
	public Seat createStandardSeatForRow(Row row){
		Seat seat = new Seat();
		seat.setState(SeatState.ENABLED);
		seat.setRow(row);
		
		return seat;
	}
	
	public Row createStandardRowForTheater(Theater theater) {
		Row row = new Row();
		row.setSeats(new ArrayList<Seat>() );
		row.setTheater(theater);
		
		return row;
	}
}
