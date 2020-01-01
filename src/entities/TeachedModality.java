package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
@NamedQueries({
        @NamedQuery(
                name = "getAllTeachedModalities",
                query = "SELECT tm FROM TeachedModality tm ORDER BY tm.id"
        )
})
@Entity
public class TeachedModality implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Modality modality;
    @ManyToOne
    private Echelon echelon;
    @ManyToOne
    private Coach coach;
    @ManyToMany
    private Set<Schedule> schedules;

    public TeachedModality() {
        this.schedules = new LinkedHashSet<>();
    }

    public TeachedModality(Modality modality, Coach coach, Echelon echelon) {
        this.modality = modality;
        this.coach = coach;
        this.echelon = echelon;
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

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
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
