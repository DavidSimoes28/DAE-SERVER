package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.LinkedHashSet;
import java.util.Set;

@NamedQueries({
        @NamedQuery(
                name = "getAllSchedules",
                query = "SELECT s FROM Schedule s ORDER BY s.id"
        )
})
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
    @ManyToMany(mappedBy = "schedules")
    private Set<PracticedModality> practicedModalities;
    @ManyToMany(mappedBy = "schedules")
    private Set<TeachedModality> teachedModalities;
    @ManyToMany(mappedBy = "schedules")
    private Set<Modality> modalities;

    public Schedule() {
        this.practicedModalities = new LinkedHashSet<>();
        this.teachedModalities = new LinkedHashSet<>();
        this.modalities = new LinkedHashSet<>();
    }

    public Schedule(DayOfWeek dayOfWeek, HourTime startDate, HourTime endDate) {
        this.dayOfWeek = dayOfWeek;
        this.startDate = startDate;
        this.endDate = endDate;
        this.practicedModalities = new LinkedHashSet<>();
        this.teachedModalities = new LinkedHashSet<>();
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

    public Set<PracticedModality> getPracticedModalities() {
        return practicedModalities;
    }

    public void setPracticedModalities(Set<PracticedModality> practicedModalities) {
        this.practicedModalities = practicedModalities;
    }

    public Set<Modality> getModalities() {
        return modalities;
    }

    public void setModalities(Set<Modality> modalities) {
        this.modalities = modalities;
    }

    public void addModality(Modality modality) {
        this.modalities.add(modality);
    }

    public void removeModality(Modality modality) {
        this.modalities.remove(modality);
    }

    public Set<TeachedModality> getTeachedModalities() {
        return teachedModalities;
    }

    public void setTeachedModalities(Set<TeachedModality> teachedModalities) {
        this.teachedModalities = teachedModalities;
    }

    public void addTeachedModality(TeachedModality teachedModality) {
        this.teachedModalities.add(teachedModality);
    }
    public void removeTeachedModality(TeachedModality teachedModality) {
        this.teachedModalities.remove(teachedModality);
    }
}
