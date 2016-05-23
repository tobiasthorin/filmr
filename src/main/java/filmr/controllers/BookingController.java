package filmr.controllers;

import filmr.domain.Booking;
import filmr.domain.Showing;
import filmr.helpers.exceptions.FilmrBaseException;
import filmr.helpers.exceptions.FilmrPOSTRequestWithPredefinedIdException;
import filmr.helpers.exceptions.FilmrPUTRequestWithMissingEntityIdException;
import filmr.helpers.exceptions.booking.FilmerBookingMustHaveSeatsException;
import filmr.services.BookingService;
import filmr.services.ShowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        	throw new FilmerBookingMustHaveSeatsException();
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
