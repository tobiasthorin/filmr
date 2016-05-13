package filmr.domain;

import javax.persistence.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
public class Seat {
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "row_id")
	private Row row;

	private String seatLabel;

	@Enumerated(EnumType.STRING)
	private SeatState state;

	
	public Seat() {}

	public String getSeatLabel() {
		return seatLabel;
	}

	public void setSeatLabel(String seatLabel) {
		this.seatLabel = seatLabel;
	}

	public SeatState getState() {
		return state;
	}

	public void setState(SeatState state) {
		this.state = state;
	}

	public Row getRow() {
		return row;
	}

	public void setRow(Row row) {
		this.row = row;
	}

	public Long getId() {
		return id;
	}
	
    @Override
    public boolean equals(Object object){
        if (object == null) {
            return false;
        }
        if(!(object instanceof Seat)){
            return false;
        }
        final Seat seat = (Seat)object;
        return new EqualsBuilder()
                .append(id,seat.getId())
                .append(row,seat.getRow())
                .isEquals();
    }
    
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(id)
                .append(row)
                .toHashCode();
    }

	
	
	
}
