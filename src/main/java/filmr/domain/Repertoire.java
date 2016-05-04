package filmr.domain;

import javax.persistence.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Repertoire {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

 // updating movie list is done through PUT /api/reperoires/{id}/?add_movie_with_id&remove_movie_with_id
    @ManyToMany
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Movie> movies;

    public Long getId() {
        return id;
    }

    public List<Movie> getMovies() {
    	System.out.println("movies are: " + movies);
        if (movies == null) {
            movies = new ArrayList<>(); //TODO this is kinda stupid?
        }
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
    
    @Override
    public boolean equals(Object object){
        if (object == null) {
            return false;
        }
        if(!(object instanceof Repertoire)){
            return false;
        }
        final Repertoire repertoire = (Repertoire)object;
        return new EqualsBuilder()
                .append(id,repertoire.getId())
                .append(movies,repertoire.getMovies())
                .isEquals();

    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(id)
                .append(movies)
                .toHashCode();
    }

	/*@Override
	public String toString() {
		return "Repertoire [id=" + id + ", movies=" + movies + "]";
	}*/
    
    
    
}
