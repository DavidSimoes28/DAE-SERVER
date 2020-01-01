package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@NamedQueries({
        @NamedQuery(
                name = "getAllGraduations",
                query = "SELECT a FROM Graduations a ORDER BY a.id"
        )
})
@Entity
@Table(name = "GRADUATIONS")
public class Graduations implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int minimumAge;
    @ManyToOne
    private Modality modality;
    @Version
    private int version;

    public Graduations(){
    }

    public Graduations(String name, int minimumAge, Modality modality) {
        this.name = name;
        this.minimumAge = minimumAge;
        this.modality = modality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(int minimumAge) {
        this.minimumAge = minimumAge;
    }

    public Modality getModality() {
        return modality;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }
}
