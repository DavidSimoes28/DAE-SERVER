package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@NamedQueries({
        @NamedQuery(
                name = "getAllGraduations",
                query = "SELECT a FROM Graduations a ORDER BY a.code"
        )
})
@Entity
@Table(name = "GRADUATIONS")
public class Graduations implements Serializable {
    @Id
    private String code;
    private String name;
    private int minimumAge;
    @ManyToMany(mappedBy = "graduations")
    private Set<Athlete> athletes;

    public Graduations(){
        athletes = new LinkedHashSet<>();
    }

    public Graduations(String code, String name, int minimumAge) {
        this.code = code;
        this.name = name;
        this.minimumAge = minimumAge;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(int minimumAge) {
        this.minimumAge = minimumAge;
    }

    public Set<Athlete> getAthletes() {
        return athletes;
    }

    public void setAthletes(Set<Athlete> athletes) {
        this.athletes = athletes;
    }
}
