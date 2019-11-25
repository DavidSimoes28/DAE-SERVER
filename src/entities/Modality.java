package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllModalities",
                query = "SELECT s FROM Modality s ORDER BY s.id"
        )
})
@Table(name = "MODALITIES")
public class Modality implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @ManyToOne
    private Schedule schedule;
    @ManyToMany
    @JoinTable(name = "MODALITIES_COACHES",
            joinColumns = @JoinColumn(name = "MODALITIES_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "COACHES_USERNAME", referencedColumnName = "USERNAME"))
    private Set<Coach> coaches;
    @ManyToMany
    @JoinTable(name = "MODALITIES_ATHELETES",
            joinColumns = @JoinColumn(name = "MODALITIES_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ATHELETES_USERNAME", referencedColumnName = "USERNAME"))
    private Set<Athlete> athletes;

    public Modality() {
        coaches = new LinkedHashSet<>();
        athletes = new LinkedHashSet<>();
    }

    public Modality(String name) {
        this.name = name;
        coaches = new LinkedHashSet<>();
        athletes = new LinkedHashSet<>();
    }

    public Modality(String name, Schedule schedule, Set<Coach> coaches, Set<Athlete> athletes) {
        this.name = name;
        this.schedule = schedule;
        this.coaches = new LinkedHashSet<>();
        this.athletes = new LinkedHashSet<>();
        this.coaches = coaches;
        this.athletes = athletes;
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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Set<Coach> getCoaches() {
        return coaches;
    }

    public void setCoaches(Set<Coach> coaches) {
        this.coaches = coaches;
    }

    public Set<Athlete> getAthletes() {
        return athletes;
    }

    public void setAthletes(Set<Athlete> athletes) {
        this.athletes = athletes;
    }

}
