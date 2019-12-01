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
    private String name;
    private int initialAge;
    private int finalAge;
    @ManyToMany
    private Set<Coach> coaches;
    @ManyToMany
    private Set<Partner> partners;

    public Echelon() {
        coaches = new LinkedHashSet<>();
        partners = new LinkedHashSet<>();
    }

    public Echelon(String name, int initialAge, int finalAge) {
        this.name = name;
        this.initialAge = initialAge;
        this.finalAge = finalAge;
        coaches = new LinkedHashSet<>();
        partners = new LinkedHashSet<>();
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

    public Set<Coach> getCoaches() {
        return coaches;
    }

    public void setCoaches(Set<Coach> coaches) {
        this.coaches = coaches;
    }

    public Set<Partner> getPartners() {
        return partners;
    }

    public void setPartners(Set<Partner> partners) {
        this.partners = partners;
    }
}
