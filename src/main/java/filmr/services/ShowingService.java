package filmr.services;

import filmr.domain.Showing;
import filmr.repositories.ShowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowingService {

    @Autowired
    private ShowingRepository showingRepository;

    public Showing saveShowing(Showing showing) {
        return showingRepository.saveAndFlush(showing);
    }

    public Showing readShowing(Long id) {
        return showingRepository.getOne(id);
    }

    public List<Showing> readAllShowings() {
        return showingRepository.findAll();
    }

    public void deleteShowing(Long id) {
        showingRepository.delete(id);
    }
}
