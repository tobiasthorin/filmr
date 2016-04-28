package filmr.domain;

import javax.persistence.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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
	
	public Theater getTheater() {
		return theater;
	}
	
	public void setTheater(Theater theater) {
		this.theater = theater;
	}
	
    @Override
    public boolean equals(Object object){
        if (object == null) {
            return false;
        }
        if(!(object instanceof Row)){
            return false;
        }
        final Row row = (Row)object;
        return new EqualsBuilder()
                .append(id,row.getId())
                .append(seats,row.getSeats())
                .append(theater, row.getTheater())
                .append(rowLabel, row.getRowLabel())
                .isEquals();

    }
    
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(id)
                .append(seats)
                .append(theater)
                .append(rowLabel)
                .toHashCode();
    }

	@Override
	public String toString() {
		return "Row [id=" + id + ", seats=" + seats + ", theater=" + theater + ", rowLabel=" + rowLabel + "]";
	}
	
	
	
	
	
}
