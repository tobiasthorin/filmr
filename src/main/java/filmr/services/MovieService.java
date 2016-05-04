package filmr.services;

import filmr.domain.Movie;
import filmr.domain.Repertoire;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import java.util.List;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.Query;
import java.util.ArrayList;

import java.util.Set;
import java.util.HashSet;

@Service
public class MovieService extends BaseServiceClass<Movie, Long> {


	
	@PersistenceContext
	@Autowired
	private EntityManager entityManager;


	@Autowired
	private RepertoireService repertoireService;

	
	public List<Movie> getAllMoviesNotInRepertoire(Long repertoire_id) {	
		List<Movie> matchingShowing = new ArrayList<Movie>();

		List<Movie> allMovies = readAllEntities();
		Set<Movie> repertoireMovies = repertoireService.readEntity(repertoire_id).getMovies();

		for(Movie movie : allMovies) {
			if(!repertoireMovies.contains(movie)) matchingShowing.add(movie);
		}
		return matchingShowing;
	}


}
