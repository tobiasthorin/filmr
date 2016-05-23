package filmr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@NamedQueries(
		@NamedQuery(name = "Theater.filterByCinema",
				query = "SELECT t FROM Theater t " +
						"WHERE t.cinema.id = :cinemaId")
)

public class Theater {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String name;
	private boolean disabled;
	@OneToMany(mappedBy = "theater", orphanRemoval=true)
	@Cascade(CascadeType.ALL)
	private List<Row> rows;

	@ManyToOne
	@JoinColumn(name = "cinema_id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Cinema cinema;

	@JsonIgnore
	@OneToMany(mappedBy = "theater")
	private List<Showing> showings;
	
	// used for back-end to determine if seat numbering is
	// reset for each row or not
	@NotNull
	@Column(columnDefinition="bit(1) default 0")
	private Boolean usingContinuousSeatLabeling;
	
	public Theater() {
	}


	public String getCinemaName() {
		return cinema != null ? cinema.getName() : "No associated cinema";
	}

	public long getCinemaId() {
		return cinema != null ? cinema.getId() : -1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Row> getRows() {
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}

	public List<Showing> getShowings() {
		return showings;
	}

	public void setShowings(List<Showing> showings) {
		this.showings = showings;
	}

	public Long getId() {
		return id;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	public Long getNumberOfEnabledSeats() {
		if(rows == null) return new Long(0);
		
		return rows.stream()
			.map(row -> row.getSeats()) // create a stream of lists of seats
			.flatMap(seats -> seats.stream()) // combine all the seats as one stream
			.filter(seat -> seat.getState() == SeatState.ENABLED)  // only care about enabled seats
			.count();
	}
	
	

    public Boolean isUsingContinuousSeatLabeling() {
		return usingContinuousSeatLabeling;
	}


	public void setUsingContinuousSeatLabeling(Boolean usingContinuousSeatLabeling) {
		this.usingContinuousSeatLabeling = usingContinuousSeatLabeling;
	}


	@Override
    public boolean equals(Object object){
        if (object == null) {
            return false;
        }
        if(!(object instanceof Theater)){
            return false;
        }
        final Theater theater = (Theater)object;
        return new EqualsBuilder()
                .append(id,theater.getId())
                .append(name,theater.getName())
                //.append(rows, theater.getRows()) //TODO breaks
                //.append(cinema, theater.getCinema())
                //.append(showings,theater.getShowings())
                .append(disabled, theater.isDisabled())
                .isEquals();

	}

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(id)
                .append(name)
                //.append(rows)
                //.append(cinema)
                //.append(showings)
                .toHashCode();
    }

	@Override
	public String toString() {
		return "Theater [id=" + id + ", name=" + name + ", "
				// + "rows size=" + rows != null ? "" +rows.size(): "no rows" 
				// +", cinema=" + cinema != null ?  cinema.getName() :  "no cinema" 
				// + ", showings size =" + showings != null ? ""+showings.size() : "no showings" 
				+ "]";
	}
}
