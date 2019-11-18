package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPartnerOrAthletes",
                query = "SELECT pa FROM PartnerOrAthlete pa ORDER BY pa.name"
        )
})
public class PartnerOrAthlete extends User {
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Echelon> echelons;
    @ManyToMany(mappedBy = "athletes", fetch = FetchType.EAGER)
    private Set<Modality> modalities;

    public PartnerOrAthlete() {
        echelons = new LinkedHashSet<>();
        modalities = new LinkedHashSet<>();
    }

    public PartnerOrAthlete(String username, String password, String name, String email) {
        super(username, password, name, email);
        echelons = new LinkedHashSet<>();
        modalities = new LinkedHashSet<>();
    }

    public Set<Echelon> getEchelons() {
        return echelons;
    }

    public void setEchelons(Set<Echelon> echelons) {
        this.echelons = echelons;
    }

    public Set<Modality> getModalities() {
        return modalities;
    }

    public void setModalities(Set<Modality> modalities) {
        this.modalities = modalities;
    }
}
