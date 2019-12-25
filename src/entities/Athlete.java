package entities;

import javax.persistence.*;
import java.util.Collection;
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
    private Set<PracticedModality> practicedModalities;
    @ManyToMany
    @JoinTable(name = "USERS_CLASSES",
            joinColumns = @JoinColumn(name = "COACH_USERNAME", referencedColumnName = "USERNAME"),
            inverseJoinColumns = @JoinColumn(name = "CLASSES_ID", referencedColumnName = "ID"))
    private Set<Classes> classes;

    public Athlete() {
        practicedModalities = new LinkedHashSet<>();
        classes = new LinkedHashSet<>();
    }

    public Athlete(String username, String password, String name, String email) {
        super(username, password, name, email,0.0);
        practicedModalities = new LinkedHashSet<>();
        classes = new LinkedHashSet<>();
    }

    public Set<PracticedModality> getPracticedModalities() {
        return practicedModalities;
    }

    public void setPracticedModalities(Set<PracticedModality> practicedModalities) {
        this.practicedModalities = practicedModalities;
    }

    public void addPracticedModality(PracticedModality practicedModality) {
        this.practicedModalities.add(practicedModality);
    }

    public void removePracticedModality(PracticedModality practicedModality) {
        this.practicedModalities.remove(practicedModality);
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

    public Set<Modality> getModalities() {
        Set<Modality> modalities = new LinkedHashSet<>();
        for (PracticedModality practicedModality : practicedModalities) {
            if(practicedModality.getModality() != null)
                modalities.add(practicedModality.getModality());
        }
        return modalities;
    }

    public Set<Echelon> getEchelons() {
        Set<Echelon> echelons = new LinkedHashSet<>();
        for (PracticedModality practicedModality : practicedModalities) {
            if(practicedModality.getEchelon() != null)
                echelons.add(practicedModality.getEchelon());
        }
        return echelons;
    }
    public Set<Graduations> getGraduations() {
        Set<Graduations> graduations = new LinkedHashSet<>();
        for (PracticedModality practicedModality : practicedModalities) {
            if(practicedModality.getGraduations() != null)
                graduations.add(practicedModality.getGraduations());
        }
        return graduations;
    }
}
