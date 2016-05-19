package filmr.controllers;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.UsesSunHttpServer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import filmr.domain.Booking;
import filmr.domain.Seat;
import filmr.domain.SeatState;
import filmr.domain.Showing;
import filmr.helpers.exceptions.FilmrBaseException;
import filmr.helpers.exceptions.FilmrExceptionModel;
import filmr.helpers.exceptions.FilmrInvalidBookingException;
import filmr.helpers.exceptions.FilmrStatusCode;
import filmr.services.BookingService;
import filmr.services.ShowingService;

@RestController
@RequestMapping(value = "/api/bookings")
public class BookingController {

    private static org.apache.log4j.Logger log = Logger.getLogger(BookingController.class);

    @Autowired
    private BookingService bookingService;
    @Autowired
    private ShowingService showingService;
//    @Autowired
//    private SeatService seatService;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Booking> createBooking(
    		@RequestBody Booking booking,
    		@RequestParam(value="for_showing_with_id", required=true) Long for_showing_with_id
    		) throws FilmrInvalidBookingException{

        if (booking.getId() != null) {
            log.warn("id on booking is not permitted when create booking");
            return new ResponseEntity<Booking>(new Booking(), HttpStatus.BAD_REQUEST);
        }
        
        if (booking.getBookedSeats() == null) {
        	throw new FilmrInvalidBookingException("Booking must have seats", FilmrStatusCode.F422);
        } 
        
        Showing showing = showingService.readEntity(for_showing_with_id);
        booking.setShowing(showing);
        
        
        bookingService.validateBooking(booking, showing);
        
        
        Booking savedBooking = bookingService.saveEntity(booking);
        return new ResponseEntity<Booking>(savedBooking, HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Booking> readBooking(@PathVariable Long id){
        Booking retrievedBooking = bookingService.readEntity(id);
        return new ResponseEntity<Booking>(retrievedBooking, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Booking>> readAllBookings() {
        List<Booking> retrievedBookings = bookingService.readAllEntities();
        return new ResponseEntity<List<Booking>>(retrievedBookings, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking Booking){
        if(Booking.getId() == null){
            log.warn("id on booking is required when update booking");
            return new ResponseEntity<Booking>(new Booking(), HttpStatus.BAD_REQUEST);
        }

        Booking updatedBooking = bookingService.saveEntity(Booking);
        return new ResponseEntity<Booking>(updatedBooking, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBooking(@PathVariable Long id) {
        bookingService.deleteEntity(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(FilmrInvalidBookingException.class)
//    public String errorerrroorrr() {
//    	return "this is an errro!!!!";
//    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(FilmrInvalidBookingException.class)
    @ResponseBody
    public FilmrExceptionModel handleBadRequest(HttpServletRequest req, FilmrInvalidBookingException ex) {
    	log.warn("Catching custom error in controller.. ");
        return new FilmrExceptionModel(req, ex);
    } 
}
