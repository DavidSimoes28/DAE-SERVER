package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllAthletes",
                query = "SELECT a FROM Athlete a ORDER BY a.name"
        )
})
public class Athlete extends Partner{
    @ManyToMany
    private Set<Echelon> echelons;
    @ManyToMany
    private Set<Graduations> graduations;

    public Athlete() {
        echelons = new LinkedHashSet<>();
        graduations = new LinkedHashSet<>();
    }

    public Athlete(String username, String password, String name, String email) {
        super(username, password, name, email);
        echelons = new LinkedHashSet<>();
        graduations = new LinkedHashSet<>();
    }

    public Set<Echelon> getEchelons() {
        return echelons;
    }

    public void setEchelons(Set<Echelon> echelons) {
        this.echelons = echelons;
    }

    public Set<Graduations> getGraduations() {
        return graduations;
    }

    public void setGraduations(Set<Graduations> graduations) {
        this.graduations = graduations;
    }
}
