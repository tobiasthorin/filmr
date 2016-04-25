package filmr.controllers;

import filmr.domain.Booking;
import filmr.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Booking> createMovie(@RequestBody Booking booking) {
        Booking savedBooking = bookingService.saveEntity(booking);
        return new ResponseEntity<Booking>(savedBooking, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Booking> readBooking(@PathVariable Long id){
        Booking retrievedShowing = bookingService.readEntity(id);
        return new ResponseEntity<Booking>(retrievedShowing, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Booking>> readAllShowings() {
        List<Booking> retrievedShowings = bookingService.readAllEntities();
        return new ResponseEntity<List<Booking>>(retrievedShowings, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking Booking){
        if(Booking.getId() == null){
            return new ResponseEntity<Booking>(new Booking(), HttpStatus.BAD_REQUEST);
        }

        Booking updatedShowing = bookingService.saveEntity(Booking);
        return new ResponseEntity<Booking>(updatedShowing, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBooking(@PathVariable Long id) {
        bookingService.deleteEntity(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
