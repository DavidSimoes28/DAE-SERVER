package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@NamedQueries({
        @NamedQuery(
                name = "getAllEchelons",
                query = "SELECT e FROM Echelon e ORDER BY e.name"
        )
})
@Entity
@Table(name = "ECHELONS")
public class Echelon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int initialAge;
    private int finalAge;
    @ManyToOne
    private Modality modality;
    @Version
    private int version;

    public Echelon() {
    }

    public Echelon(String name, int initialAge, int finalAge, Modality modality) {
        this.name = name;
        this.initialAge = initialAge;
        this.finalAge = finalAge;
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

    public int getInitialAge() {
        return initialAge;
    }

    public void setInitialAge(int initialAge) {
        this.initialAge = initialAge;
    }

    public int getFinalAge() {
        return finalAge;
    }

    public void setFinalAge(int finalAge) {
        this.finalAge = finalAge;
    }

    public Modality getModality() {
        return modality;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }
}
