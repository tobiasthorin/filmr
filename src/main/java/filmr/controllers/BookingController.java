package filmr.controllers;

import java.util.ArrayList;
import java.util.List;
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
import filmr.domain.Showing;
import filmr.services.BookingService;
import filmr.services.SeatService;
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
        
        // TODO: ? check if seat exists in showing theater
//        List<Seat> savedSeats = new ArrayList<Seat>();
//        booking.getBookedSeats().forEach(seat -> {
//        	savedSeats.add(seatService.readEntity(seat.getId()));
//        });
//        booking.setBookedSeats(savedSeats);
        
        // check if any of the seats are already booked
        List<Seat> alreadyBookedSeatsForShowing = showing.getBookings().stream()
        		.map(b -> b.getBookedSeats() )
        		.flatMap(seats -> seats.stream())
        		.distinct()
        		.collect(Collectors.toList());
        
        List<Seat> doubleBookedSeats = booking.getBookedSeats().stream()
        		.filter(seat -> alreadyBookedSeatsForShowing.contains(seat))
        		.distinct()
        		.collect(Collectors.toList());
        
        if(doubleBookedSeats.size() != 0) {
        	StringBuilder stringBuilder = new StringBuilder();
        	for(int i = 0; i < doubleBookedSeats.size(); i++) {
        		Seat doubleBookedSeat = doubleBookedSeats.get(i);
        		stringBuilder.append("seat with label ").append(doubleBookedSeat.getSeatLabel()).append(",");
        		//TODO: exclude last comma...
        	}
        	
        	throw new Exception("Double booked seats for " + stringBuilder.toString());
        }
        
        
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

}
