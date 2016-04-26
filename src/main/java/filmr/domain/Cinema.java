package filmr.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;

    @OneToOne
	private Repertoire repertoire;
    @OneToMany(mappedBy = "cinema")
	private List<Theater> theaters;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Repertoire getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(Repertoire repertoire) {
        this.repertoire = repertoire;
    }

    public List<Theater> getTheaters() {
        return theaters;
    }

    public void setTheaters(List<Theater> theaters) {
        this.theaters = theaters;
    }
}
