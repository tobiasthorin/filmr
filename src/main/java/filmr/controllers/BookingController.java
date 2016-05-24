package filmr.controllers;

import java.time.LocalDateTime;
import java.util.List;

import filmr.helpers.exceptions.booking.FilmrBookingPastDateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import filmr.domain.Booking;
import filmr.domain.Showing;
import filmr.helpers.exceptions.FilmrBaseException;
import filmr.helpers.exceptions.FilmrPOSTRequestWithPredefinedIdException;
import filmr.helpers.exceptions.FilmrPUTRequestWithMissingEntityIdException;
import filmr.helpers.exceptions.booking.FilmrBookingMustHaveSeatsException;
import filmr.services.BookingService;
import filmr.services.ShowingService;

@RestController
@RequestMapping(value = "/api/bookings")
public class BookingController extends BaseController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private ShowingService showingService;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Booking> createBooking(
    		@RequestBody Booking booking,
    		@RequestParam(value="for_showing_with_id", required=true) Long for_showing_with_id
    		) throws FilmrBaseException{

        if (booking.getId() != null) {
            logger.warn("Pre-set id on booking is not permitted when creating booking");
            throw new FilmrPOSTRequestWithPredefinedIdException("Pre-set id on booking is not permitted when creating booking");
        }
        
        if (booking.getBookedSeats() == null || booking.getBookedSeats().size() == 0) {
        	throw new FilmrBookingMustHaveSeatsException();
        } 
        
        Showing showing = showingService.readEntity(for_showing_with_id);
	    if(showing.getShowDateTime().isBefore(LocalDateTime.now())){
			throw new FilmrBookingPastDateException();
	    }

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
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking Booking) throws FilmrPUTRequestWithMissingEntityIdException{
        if(Booking.getId() == null){
            logger.warn("id on booking is required when update booking");
            throw new FilmrPUTRequestWithMissingEntityIdException("id on booking is required when update booking");
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
    
}
