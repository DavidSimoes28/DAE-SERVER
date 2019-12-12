package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@NamedQueries({
        @NamedQuery(
                name = "getAllPracticedModalities",
                query = "SELECT pm FROM PracticedModality pm ORDER BY pm.id"
        )
})
@Entity
@Table(name = "PRACTICEDMODALITIES")
public class PracticedModality implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @ManyToOne
    private Modality modality;
    @ManyToOne
    private Echelon echelon;
    @ManyToOne
    private Graduations graduations;
    @ManyToOne
    private Athlete athlete;
    @ManyToMany
    private Set<Schedule> schedules;

    public PracticedModality() {
    }

    public PracticedModality(Modality modality, Echelon echelon, Athlete athlete) {
        this.modality = modality;
        this.echelon = echelon;
        this.athlete = athlete;
        this.schedules = new LinkedHashSet<>();
    }

    public PracticedModality(Modality modality, Graduations graduations, Athlete athlete) {
        this.modality = modality;
        this.graduations = graduations;
        this.athlete = athlete;
        this.schedules = new LinkedHashSet<>();
    }

    public PracticedModality(Modality modality, Echelon echelon, Graduations graduations, Athlete athlete) {
        this.modality = modality;
        this.echelon = echelon;
        this.graduations = graduations;
        this.athlete = athlete;
        this.schedules = new LinkedHashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Modality getModality() {
        return modality;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }

    public Echelon getEchelon() {
        return echelon;
    }

    public void setEchelon(Echelon echelon) {
        this.echelon = echelon;
    }

    public Graduations getGraduations() {
        return graduations;
    }

    public void setGraduations(Graduations graduations) {
        this.graduations = graduations;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    public void addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
    }

    public void removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
    }
}
