package filmr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Entity
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String bookingReference;
	
	@Size(min=1)
	@ManyToMany //TODO changed from ManyToOne, beware of side effects!!!!!1one!!
	private List<Seat> bookedSeats;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "showing_id")
	private Showing showing;
	
	@NotEmpty
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
	
	
	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (!(object instanceof Booking)) {
			return false;
		}
		final Booking booking = (Booking) object;
		return new EqualsBuilder()
				.append(id, booking.getId())
				.append(bookingReference, booking.getBookingReference())
				.append(bookedSeats, booking.getBookedSeats())
				.append(showing, booking.getShowing())
				.append(phoneNumber, booking.getPhoneNumber())
				.isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31)
				.append(id)
				.append(bookingReference)
				.append(bookedSeats)
				.append(showing)
				.append(phoneNumber)
				.toHashCode();
	}

	/*@Override
	public String toString() {
		return "Booking [id=" + id + ", bookingReference=" + bookingReference + ", bookedSeats=" + bookedSeats
				+ ", showing=" + showing + ", phoneNumber=" + phoneNumber + "]";
	}*/
	
	
	
	
}
