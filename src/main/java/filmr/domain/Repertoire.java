package filmr.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Repertoire {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
	private List<Movie> movies;

    public Long getId() {
        return id;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
