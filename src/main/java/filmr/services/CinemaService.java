package filmr.services;

import filmr.domain.Cinema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class CinemaService extends BaseServiceClass<Cinema, Long> {

//    @PersistenceContext
//    @Autowired
//    private EntityManager entityManager;
//
//    public List<Cinema> getAllMatchingParams(Boolean show_disabled_cinemas) {
//        Query query = entityManager.createNamedQuery("Cinema.filteredAndOrdered", Cinema.class);
//        query.setParameter("showDisabledShowings", show_disabled_cinemas);
//        List<Cinema> matchingCinemas = query.getResultList();
//        String queryBeingMade = query.unwrap(org.hibernate.Query.class).getQueryString();
//        return matchingCinemas;
//    }
}
