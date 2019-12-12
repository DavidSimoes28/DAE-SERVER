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
    @OneToMany(mappedBy = "athlete", fetch = FetchType.EAGER)
    private Set<PracticedModality> modalities;

    public Athlete() {
        modalities = new LinkedHashSet<>();
    }

    public Athlete(String username, String password, String name, String email) {
        super(username, password, name, email);
        modalities = new LinkedHashSet<>();
    }

    public Set<PracticedModality> getModalities() {
        return modalities;
    }

    public void setModalities(Set<PracticedModality> modalities) {
        this.modalities = modalities;
    }

    public void addModality(PracticedModality modality) {
        this.modalities.add(modality);
    }

    public void removeModality(PracticedModality modality) {
        this.modalities.remove(modality);
    }
}
