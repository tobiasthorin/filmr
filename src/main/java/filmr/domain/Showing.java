package filmr.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Showing {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Date showDateTime;
	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;
	@ManyToOne
	@JoinColumn(name = "theater_id")
	private Theater theater;
	@OneToMany(mappedBy = "showing")
	private List<Booking> bookings;
	
	public Showing() {}



	public Date getShowingDateTime() {
		return showDateTime;
	}

	public void setShowingDateTime(Date showingDateTime) {
		this.showDateTime = showingDateTime;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Theater getTheater() {
		return theater;
	}

	public void setTheater(Theater theater) {
		this.theater = theater;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public Long getId() {
		return id;
	}
	
	
}
