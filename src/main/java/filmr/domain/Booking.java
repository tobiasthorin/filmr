package filmr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String bookingReference;
	@OneToMany
	private List<Seat> bookedSeats;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "showing_id")
	private Showing showing;
	
	private String phoneNumber;
	
	public Booking() {
		
	}

	public String getBookingReference() {
		return bookingReference;
	}

	public void setBookingReference(String bookingReference) {
		this.bookingReference = bookingReference;
	}

	public List<Seat> getBookedSeats() {
		return bookedSeats;
	}

	public void setBookedSeats(List<Seat> bookedSeats) {
		this.bookedSeats = bookedSeats;
	}

	public Showing getShowing() {
		return showing;
	}

	public void setShowing(Showing showing) {
		this.showing = showing;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getId() {
		return id;
	}
	
	
}
