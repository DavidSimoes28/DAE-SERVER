package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@NamedQueries({
        @NamedQuery(
                name = "getAllGraduations",
                query = "SELECT a FROM Graduations a ORDER BY a.id"
        )
})
@Entity
@Table(name = "GRADUATIONS")
public class Graduations implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @ManyToMany(mappedBy = "graduations")
    private Set<Athlete> athletes;

    public Graduations(String name) {
        this.name = name;
        athletes = new LinkedHashSet<>();
    }

    public Graduations(){
        athletes = new LinkedHashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Athlete> getAthletes() {
        return athletes;
    }

    public void setAthletes(Set<Athlete> athletes) {
        this.athletes = athletes;
    }
}
