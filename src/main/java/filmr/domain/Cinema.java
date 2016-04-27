package filmr.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @NotNull
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

    @Override
    public boolean equals(Object object){
        System.out.println("check null");
        if (object == null) {
            return false;
        }
        System.out.println("check instance of cinema");
        if(!(object instanceof Cinema)){
            return false;
        }
        System.out.println("instance of true");
        final Cinema cinema = (Cinema)object;
        return new EqualsBuilder()
                .append(id,cinema.getId())
                .append(name,cinema.getName())
                .append(repertoire,cinema.getRepertoire())
                .append(theaters, cinema.getTheaters())
                .isEquals();

    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(id)
                .append(name)
                .append(repertoire)
                .append(theaters)
                .toHashCode();
    }

}
