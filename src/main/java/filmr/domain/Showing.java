package filmr.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import filmr.helpers.CustomJsonDateDeserializer;

import java.time.LocalDateTime;
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
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


@Entity
@NamedQueries(
		@NamedQuery(name = "Showing.filteredAndOrdered", 
					query = "SELECT s FROM Showing s " + 
							"WHERE " +
							"( (:showDisabledShowings = TRUE) OR (s.isDisabled = FALSE OR s.isDisabled is null) )  AND " + // (s.isDisabled = false) will only be evaluated if showDisabledShowings = false, and will only evaluate to true if s is not disabled
							"(:fromDate is null OR s.showDateTime > :fromDate) AND " +
							"(:toDate is null OR s.showDateTime <= :toDate) AND " + // only care about the date, not time
							"(:onlyForMovieWithId is null OR s.movie.id = :onlyForMovieWithId) AND " +
							"(:onlyForTheaterWithId is null OR s.theater.id = :onlyForTheaterWithId) AND " +
							"(:onlyForCinemaWithId is null OR s.theater.cinema.id = :onlyForCinemaWithId) " +
							"ORDER BY s.showDateTime ASC"
				)
		)
public class Showing implements Comparable<Showing> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@JsonDeserialize(using=CustomJsonDateDeserializer.class)
	private LocalDateTime showDateTime;
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
	
	@PrePersist // a way to set default values for properties (for entities created non-manually, i.e. not through sql inserts)
	private void prePersist() {
		if(isDisabled == null) {
			isDisabled = false;
		}
	}
	


	public LocalDateTime getShowDateTime() {
		return showDateTime;
	}

	public void setShowDateTime(LocalDateTime showDateTime) {
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

	public Boolean getIsDisabled() {
		return isDisabled != null ? isDisabled : new Boolean(false) ;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public Long getId() {
		return id;
	}
	
	public LocalDateTime getShowingEndTime() {
        if(movie==null){
            return null;
        }
		LocalDateTime movieEndTime = showDateTime.plusMinutes(movie.getLengthInMinutes()); 
		return movieEndTime;

	}

//	//TODO: remove. just testing stuff
//	public String getShowingStartTimeAsString() {
//		return showDateTime.toString();
//	}
//
//	//TODO: remove. just testing stuff
//	public String getShowingEndTimeAsString() {
//		return getShowingEndTime().toString();
//	}

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



	/*@Override
	public String toString() {
		return "Showing [id=" + id + ", showDateTime=" + showDateTime + ", movie=" + movie.getTitle() + ", theater=" + theater.getName()
				+ ", bookings size =" + bookings.size() + ", isDisabled=" + isDisabled + "]";
	} */
	
	@Override
	public int compareTo(Showing o) {
		// Showings are by default sorted by date, so we can use the Date's compareTo-method
		return this.showDateTime.compareTo(o.getShowDateTime());
	}
	
}
