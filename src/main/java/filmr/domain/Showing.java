package filmr.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;


@Entity
@NamedQueries(
		@NamedQuery(name = "Showing.filteredAndOrdered", 
					query = "SELECT s FROM Showing s " + 
							"WHERE " +
							"(:fromDate is null OR s.showDateTime > :fromDate) AND " +
							"(:toDate is null OR FUNCTION('DATE_FORMAT', s.showDateTime, '%Y-%m-%d') <= FUNCTION('DATE_FORMAT', :toDate, '%Y-%m-%d')) AND " + // only care about the date, not time
							"(:onlyForMovieWithId is null OR s.movie.id = :onlyForMovieWithId) AND " +
							"(:onlyForTheaterWithId is null OR s.theater.id = :onlyForTheaterWithId) " +
							"ORDER BY s.showDateTime ASC"
				)
		)
public class Showing {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private Date showDateTime;
	@ManyToOne
	@JoinColumn(name = "movie_id")
	@NotNull
	private Movie movie;
	@ManyToOne
	@JoinColumn(name = "theater_id")
	@NotNull
	private Theater theater;
	@OneToMany(mappedBy = "showing")
	private List<Booking> bookings;
	private Boolean isDisabled;
	
	public Showing() {}



	public Date getShowDateTime() {
		return showDateTime;
	}

	public void setShowDateTime(Date showDateTime) {
		this.showDateTime = showDateTime;
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
	
	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (!(object instanceof Showing)) {
			return false;
		}
		final Showing showing = (Showing) object;
		return new EqualsBuilder()
				.append(id, showing.getId())
				.append(showDateTime, showing.getShowDateTime())
				.append(movie, showing.getMovie())
				.append(theater, showing.getTheater())
				.append(bookings, showing.getBookings())
				.append(isDisabled, showing.isDisabled)
				.isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31)
				.append(id)
				.append(showDateTime)
				.append(movie)
				.append(theater)
				.append(bookings)
				.append(isDisabled)
				.toHashCode();
	}



	@Override
	public String toString() {
		return "Showing [id=" + id + ", showDateTime=" + showDateTime + ", movie=" + movie + ", theater=" + theater
				+ ", bookings=" + bookings + ", isDisabled=" + isDisabled + "]";
	}
	
	
}
