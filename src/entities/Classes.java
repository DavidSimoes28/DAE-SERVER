package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@NamedQueries({
        @NamedQuery(
                name = "getAllClasses",
                query = "SELECT c FROM Classes c ORDER BY c.date"
        )
})
@Entity
public class Classes implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Coach coach;
    @ManyToOne
    private Schedule schedule;
    @ManyToMany
    private Set<Athlete> athletesPresent;
    @ManyToOne
    private Modality modality;
    private Date date;
    @Version
    private int version;

    public Classes() {
        athletesPresent = new LinkedHashSet<>();
    }

    public Classes(Coach coach, Schedule schedule, Modality modality, Date date) {
        this.coach = coach;
        this.schedule = schedule;
        this.modality = modality;
        this.date = date;
        athletesPresent = new LinkedHashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Set<Athlete> getAthletesPresent() {
        return athletesPresent;
    }

    public void setAthletesPresent(Set<Athlete> athletesPresent) {
        athletesPresent = athletesPresent;
    }

    public void addAthletesPresent(Athlete athletePresent) {
        athletesPresent.add(athletePresent);
    }

    public void removeAthletesPresent(Athlete athletePresent) {
        athletesPresent.remove(athletePresent);
    }

    public Modality getModality() {
        return modality;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
