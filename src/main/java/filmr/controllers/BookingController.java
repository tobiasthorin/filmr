package filmr.controllers;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
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
import filmr.domain.Seat;
import filmr.domain.SeatState;
import filmr.domain.Showing;
import filmr.helpers.exceptions.FilmrInvalidBookingException;
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
    		) throws Exception{

        if (booking.getId() != null) {
            log.warn("id on booking is not permitted when create booking");
            return new ResponseEntity<Booking>(new Booking(), HttpStatus.BAD_REQUEST);
        }
        
        if (booking.getBookedSeats() == null) {
        	throw new Exception("Booking must have seats");
        } 
        
        Showing showing = showingService.readEntity(for_showing_with_id);
        booking.setShowing(showing);
        
        
        validateBooking(booking, showing);
        
        
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


    
    private void validateBooking(Booking booking, Showing showing) throws Exception {
    	checkIfSeatsBelongToRightTheater(booking, showing);
    	checkIfDoubleBooked(booking, showing);
    	
    }
    
    
    // also handles the situation where someone tries to book a seat with status of something other than ENABLED
    private void checkIfSeatsBelongToRightTheater(Booking booking, Showing showing) throws FilmrInvalidBookingException {
		List<Seat> validSeats = showing.getTheater().getRows().stream()
				.flatMap(row -> row.getSeats().stream())
				.filter(isEnabledSeat)
				.collect(Collectors.toList());
		
		List<Seat> seatsToBook = booking.getBookedSeats();
	
		// all seats to book should have their counterparts in the list of valid seats
		List<Seat> invalidSeats = seatsToBook.stream()
			.filter(seatHasIdCounterpartInList(validSeats).negate())
			.collect(Collectors.toList());
		
		if(invalidSeats.size() != 0) {
    		StringBuilder stringBuilder = new StringBuilder("Invalid seats for theater ").append(showing.getTheater().getName()).append(". Seat(s) with label ");
    		for(int i = 0; i < invalidSeats.size(); i++) {
    			Seat invalidSeat = invalidSeats.get(i);
    			stringBuilder.append("'").append(invalidSeat.getSeatLabel()).append("'");
    			
    			if(i < invalidSeats.size() -1) {
    				stringBuilder.append(", ");        			
    			}
    		}
    		stringBuilder.append(".");
    		
    		String errorMessage = stringBuilder.toString();
    		log.warn(errorMessage);
    		throw new FilmrInvalidBookingException(errorMessage);
		}
	}


	/**
     * Finds doubly booked seats (if they exist) and throws error.
     * @param booking to get the seats that is currently being booked
     * @param showing to get the seats already booked
	 * @throws FilmrInvalidBookingException 
     * @throws Exception
     */
    private void checkIfDoubleBooked(Booking booking, Showing showing) throws FilmrInvalidBookingException {
    	List<Seat> alreadyBookedSeatsForShowing = showing.getBookings().stream()
    			.map(b -> b.getBookedSeats() )
    			.flatMap(seats -> seats.stream())
    			.distinct()
    			.collect(Collectors.toList());
    	
    	List<Seat> doubleBookedSeats = booking.getBookedSeats().stream()
    			.filter(seatHasIdCounterpartInList(alreadyBookedSeatsForShowing))
    			.distinct()
    			.collect(Collectors.toList());
    	
    	if(doubleBookedSeats.size() != 0) {
    		// build error message noting which seats where already booked
    		StringBuilder stringBuilder = new StringBuilder("Double-booked seats! For seats with label(s) ");
    		for(int i = 0; i < doubleBookedSeats.size(); i++) {
    			Seat doubleBookedSeat = doubleBookedSeats.get(i);
    			stringBuilder.append("'").append(doubleBookedSeat.getSeatLabel()).append("'");
    			if(i < doubleBookedSeats.size() -1) {
    				stringBuilder.append(", ");        			
    			}
    		}
    		stringBuilder.append(".");
    		
    		String errorMessage = stringBuilder.toString();
    		log.warn(errorMessage);
    		throw new FilmrInvalidBookingException(errorMessage);
    	}
    }
    
    private Predicate<Seat> seatHasIdCounterpartInList(List<Seat> seatsToCheckAgainst) {
    	return seatToCheck -> seatsToCheckAgainst.stream().anyMatch(validSeat -> seatToCheck.getId() == validSeat.getId()) ;
    }
    
    private Predicate<Seat> isEnabledSeat = seat -> seat.getState() == SeatState.ENABLED;
    
    
}
