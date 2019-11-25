package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "SCHEDULE")
public class Schedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private DayOfWeek dayOfWeek;
    @ManyToOne
    private HourTime startDate;
    @ManyToOne
    private HourTime endDate;
    @OneToMany(mappedBy = "schedule")
    private Set<Modality> modalities;

    public Schedule() {
        this.modalities = new LinkedHashSet<>();
    }

    public Schedule(DayOfWeek dayOfWeek, HourTime startDate, HourTime endDate) {
        this.dayOfWeek = dayOfWeek;
        this.startDate = startDate;
        this.endDate = endDate;
        this.modalities = new LinkedHashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DayOfWeek getDayOfTheWeek() {
        return dayOfWeek;
    }

    public void setDayOfTheWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public HourTime getStartDate() {
        return startDate;
    }

    public void setStartDate(HourTime startDate) {
        this.startDate = startDate;
    }

    public HourTime getEndDate() {
        return endDate;
    }

    public void setEndDate(HourTime endDate) {
        this.endDate = endDate;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Set<Modality> getModalities() {
        return modalities;
    }

    public void setModalities(Set<Modality> modalities) {
        this.modalities = modalities;
    }
}
