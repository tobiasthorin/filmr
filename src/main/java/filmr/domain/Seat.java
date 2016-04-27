package filmr.domain;

import javax.persistence.*;

@Entity
public class Seat {
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "row_id")
	private Row row;
	
	public Seat() {}

	public Row getRow() {
		return row;
	}

	public void setRow(Row row) {
		this.row = row;
	}

	public Long getId() {
		return id;
	}
	
	
	
}
