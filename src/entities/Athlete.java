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
    @ManyToMany
    @JoinTable(name = "USERS_CLASSES",
            joinColumns = @JoinColumn(name = "COACH_USERNAME", referencedColumnName = "USERNAME"),
            inverseJoinColumns = @JoinColumn(name = "CLASSES_ID", referencedColumnName = "ID"))
    private Set<Classes> classes;

    public Athlete() {
        modalities = new LinkedHashSet<>();
        classes = new LinkedHashSet<>();
    }

    public Athlete(String username, String password, String name, String email) {
        super(username, password, name, email,0.0);
        modalities = new LinkedHashSet<>();
        classes = new LinkedHashSet<>();
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

    public Set<Classes> getClasses() {
        return classes;
    }

    public void setClasses(Set<Classes> classes) {
        this.classes = classes;
    }

    public void addClass(Classes classes) {
        this.classes.add(classes);
    }

    public void removeClass(Classes classes) {
        this.classes.remove(classes);
    }
}
