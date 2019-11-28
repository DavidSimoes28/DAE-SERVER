package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@NamedQueries({
        @NamedQuery(
                name = "getAllEchelons",
                query = "SELECT e FROM Echelon e ORDER BY e.echelon"
        )
})
@Entity
@Table(name = "ECHELONS")
public class Echelon implements Serializable {
    @Id
    private String echelon;
    @ManyToMany
    private Set<Coach> coaches;
    @ManyToMany
    private Set<Partner> partners;

    public Echelon() {
    }

    public Echelon(String echelon) {
        this.echelon = echelon;
    }

    public String getEchelon() {
        return echelon;
    }

    public void setEchelon(String echelon) {
        this.echelon = echelon;
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
