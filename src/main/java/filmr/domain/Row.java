package filmr.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Row {
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	@OneToMany
	private List<Seat> seats;
	private String rowLabel;
	
	public Row() {}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public String getRowLabel() {
		return rowLabel;
	}

	public void setRowLabel(String rowLabel) {
		this.rowLabel = rowLabel;
	}

	public Long getId() {
		return id;
	}
	
	
	
}
