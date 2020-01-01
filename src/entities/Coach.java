package entities;

import javax.persistence.*;
import java.util.Collection;
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
    @OneToMany
    private Set<TeachedModality> teachedModalities;
    @OneToMany
    private Set<Classes> classes;

    public Coach() {
        teachedModalities = new LinkedHashSet<>();
        classes = new LinkedHashSet<>();
    }

    public Coach(String username, String password, String name, String email) {
        super(username, password, name, email);
        teachedModalities = new LinkedHashSet<>();
        classes = new LinkedHashSet<>();
    }

    public Set<TeachedModality> getTeachedModalities() {
        return teachedModalities;
    }

    public void setTeachedModalities(Set<TeachedModality> teachedModalities) {
        this.teachedModalities = teachedModalities;
    }

    public void addTeachedModalities(TeachedModality teachedModality) {
        this.teachedModalities.add(teachedModality);
    }

    public void removeTeachedModalities(TeachedModality teachedModality) {
        this.teachedModalities.remove(teachedModality);
    }

    public Set<Classes> getClasses() {
        return classes;
    }

    public void setClasses(Set<Classes> classes) {
        this.classes = classes;
    }

    public void addClasses(Classes classes) {
        this.classes.add(classes);
    }

    public void removeClasses(Classes classes) {
        this.classes.remove(classes);
    }

    public Set<Modality> getModalities(){
        Set<Modality> modalities = new LinkedHashSet<>();
        for (TeachedModality teachedModality : teachedModalities) {
            if(teachedModality.getCoach()!=null)
                modalities.add(teachedModality.getModality());
        }
        return modalities;
    }

    public Set<Echelon> getEchelons() {
        Set<Echelon> echelons = new LinkedHashSet<>();
        for (TeachedModality teachedModality : teachedModalities) {
            if(teachedModality.getCoach()!=null)
                echelons.add(teachedModality.getEchelon());
        }
        return echelons;
    }
}
