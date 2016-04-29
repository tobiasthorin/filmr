package filmr.testfactories;

import filmr.domain.*;

import java.util.ArrayList;

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
        int numberOfSeats;
        boolean disabled;
        ArrayList<Row> rows;
        Cinema cinema;
        ArrayList<Showing> showings;

        theaterName = name;

        numberOfSeats = 1; //TODO how to set?

        disabled = false; //TODO parameter?

        rows = new ArrayList<>(); //TODO linked in DB?

        cinema = savedCinema;

        showings = new ArrayList<>(); //TODO should be empty no errers?

        theater.setName(theaterName);
        theater.setNumberOfSeats(numberOfSeats);
        theater.setDisabled(disabled);
        theater.setRows(rows);
        theater.setCinema(cinema);
        theater.setShowings(showings);
        return theater;
    }

    public static Repertoire createRepertoire() {
        Repertoire repertoire = new Repertoire();
        ArrayList<Movie> movies = new ArrayList<>();
        repertoire.setMovies(movies);
        return repertoire;
    }

    public static Movie createMovie(String title, String description, Long lenght) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setLengthInMinutes(lenght);
        return movie;
    }
}
