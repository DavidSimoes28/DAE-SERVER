package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllCoaches",
                query = "SELECT c FROM Coach c ORDER BY c.name"
        )
})
public class Coach extends User {
    @ManyToMany(mappedBy = "coaches", fetch = FetchType.EAGER)
    private Set<Modality> modalities;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Echelon> echelons;

    public Coach() {
        echelons = new LinkedHashSet<>();
        modalities = new LinkedHashSet<>();
    }

    public Coach(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    public Set<Modality> getModalities() {
        return modalities;
    }

    public void setModalities(Set<Modality> modalities) {
        this.modalities = modalities;
    }

    public Set<Echelon> getEchelons() {
        return echelons;
    }

    public void setEchelons(Set<Echelon> echelons) {
        this.echelons = echelons;
    }
}
