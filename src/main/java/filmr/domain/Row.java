package filmr.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Row {
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	@OneToMany(mappedBy = "row")
	private List<Seat> seats;
	@ManyToOne
	@JoinColumn(name = "theater_id")
	private Theater theater;
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
