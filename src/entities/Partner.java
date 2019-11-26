package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPartners",
                query = "SELECT pa FROM Partner pa ORDER BY pa.name"
        )
})
public class Partner extends User {
    @ManyToMany(mappedBy = "athletes", fetch = FetchType.EAGER)
    private Set<Modality> modalities;

    public Partner() {
        modalities = new LinkedHashSet<>();
    }

    public Partner(String username, String password, String name, String email) {
        super(username, password, name, email);
        modalities = new LinkedHashSet<>();
    }

    public Set<Modality> getModalities() {
        return modalities;
    }

    public void setModalities(Set<Modality> modalities) {
        this.modalities = modalities;
    }
}
