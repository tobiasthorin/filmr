package filmr.services;

import filmr.controllers.BookingController;
import filmr.domain.Booking;
import filmr.domain.Seat;
import filmr.domain.SeatState;
import filmr.domain.Showing;
import filmr.helpers.exceptions.FilmrInvalidBookingException;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class BookingService extends BaseServiceClass<Booking,Long> {
	
	private static org.apache.log4j.Logger log = Logger.getLogger(BookingController.class);

	/**
     * Checks if showing is enabled and if the proposed seats to book are valid in terms of not being non-ENABLED seats, not belonging to other (or no) theater, not already booked
     * @param booking to get the seats that is currently being booked
     * @param showing to get the seats already booked
     * @throws FilmrInvalidBookingException if any of the conditions fail
     */
    public void validateBooking(Booking booking, Showing showing) throws FilmrInvalidBookingException {
    	if(showing.getIsDisabled()) throw new FilmrInvalidBookingException("Can't book seats for disabled showing");
    	
    	checkIfDoubleBooked(booking, showing);
    	checkIfSeatsBelongToRightTheater(booking, showing);
    	
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
    		stringBuilder.append(". Seats belong to other (or no) theater, or are disabled.");
    		
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
