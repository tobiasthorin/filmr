package filmr.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;


public class Cinema {

	private Long id;
	private String name;
	private Repertoire repertoire;
	private List<Theater> theaters;

}
