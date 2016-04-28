package filmr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.List;

@Entity
public class Theater {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@OneToMany(mappedBy = "theater")
	private List<Row> rows;

    @ManyToOne
    @JoinColumn(name="cinema_id")
    @JsonIgnore
    private Cinema cinema;

    @Transient
    private String cinemaName;
	
	@JsonIgnore
	@OneToMany(mappedBy = "theater")
	private List<Showing> showings;
	
	public Theater() {}

	public String getName() {
		return name;
	}

    public String getCinemaName() {
        return cinema.getName();
    }

    public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
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
                .append(rows, theater.getRows())
                .append(cinema, theater.getCinema())
                .append(showings,theater.getShowings())
                .isEquals();

    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(id)
                .append(name)
                .append(rows)
                .append(cinema)
                .append(showings)
                .toHashCode();
    }

	@Override
	public String toString() {
		return "Theater [id=" + id + ", name=" + name + ", rows size=" + rows.size() + ", cinema=" + cinema + ", showings size ="
				+ showings.size() + "]";
	}
    
    
	
	 
	
}
