package filmr.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
public class Theater {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@OneToMany(mappedBy = "theater")
	private List<Row> rows;
	
	@JsonIgnore
	@OneToMany(mappedBy = "theater")
	private List<Showing> showings;
	
	public Theater() {}

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
	
	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (!(object instanceof Theater)) {
			return false;
		}
		final Theater theater = (Theater) object;
		return new EqualsBuilder()
				.append(id, theater.getId())
				.append(name, theater.getName())
				.append(rows, theater.getRows())
				.append(showings, theater.getShowings())
				.isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31)
				.append(id)
				.append(name)
				.append(rows)
				.append(showings)
				.toHashCode();
	}
	
}
