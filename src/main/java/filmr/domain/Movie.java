package filmr.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String title;
	@NotBlank
	private String description;
	@NotNull
	@Range(min=1)
	private Long lengthInMinutes;
	@NotNull
	@Range(min=0)
	private Double defaultPrice;
	
	public Double getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(Double defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public Movie() {}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getLengthInMinutes() {
		return lengthInMinutes;
	}

	public void setLengthInMinutes(Long lengthInMinutes) {
		this.lengthInMinutes = lengthInMinutes;
	}

	public Long getId() {
		return id;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (!(object instanceof Movie)) {
			return false;
		}
		final Movie movie = (Movie) object;
		return new EqualsBuilder()
				.append(id, movie.getId())
				.append(title, movie.getTitle())
				.append(description, movie.getDescription())
				.append(lengthInMinutes, movie.getLengthInMinutes())
				.isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31)
				.append(id)
				.append(title)
				.append(description)
				.append(lengthInMinutes)
				.toHashCode();
	}

	/*@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", description=" + description + ", lengthInMinutes="
				+ lengthInMinutes + "]";
	}*/
	
	
}
