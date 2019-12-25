package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllModalities",
                query = "SELECT s FROM Modality s ORDER BY s.id"
        ),
        @NamedQuery(
                name = "getActiveModalities",
                query = "SELECT s FROM Modality s WHERE s.active = true ORDER BY s.id"
        ),
        @NamedQuery(
                name = "getDeactivatedModalities",
                query = "SELECT s FROM Modality s WHERE s.active = false ORDER BY s.id"
        )
})
@Table(name = "MODALITIES")
public class Modality implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @OneToMany(mappedBy = "modality")
    private Set<PracticedModality> practicedModalities;
    @ManyToMany
    @JoinTable(name = "MODALITIES_SCHEDULES",
            joinColumns = @JoinColumn(name = "MODALITIES_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "SCHEDULE_ID", referencedColumnName = "ID"))
    private Set<Schedule> schedules;
    @OneToMany
    private Set<Echelon> echelons;
    @OneToMany
    private Set<Graduations> graduations;
    @OneToMany
    private Set<TeachedModality> teachedModalities;
    private int sportYear;
    private boolean active;

    public Modality() {
        practicedModalities = new LinkedHashSet<>();
        schedules = new LinkedHashSet<>();
        echelons = new LinkedHashSet<>();
        graduations = new LinkedHashSet<>();
        teachedModalities = new LinkedHashSet<>();
    }

    public Modality(String name, int sportYear, boolean active) {
        this.name = name;
        this.sportYear = sportYear;
        this.active = active;
        practicedModalities = new LinkedHashSet<>();
        schedules = new LinkedHashSet<>();
        echelons = new LinkedHashSet<>();
        graduations = new LinkedHashSet<>();
        teachedModalities = new LinkedHashSet<>();
    }

    public Modality(String name, Set<PracticedModality> practicedModalities, Set<Schedule> schedules, Set<Echelon> echelons, Set<Graduations> graduations, Set<TeachedModality> teachedModalities) {
        this.name = name;
        this.practicedModalities = practicedModalities;
        this.schedules = schedules;
        this.echelons = echelons;
        this.graduations = graduations;
        this.teachedModalities = teachedModalities;
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

    public void addAthlete(PracticedModality practicedModalities) {
        this.practicedModalities.add(practicedModalities);
    }

    public void removeAthlete(PracticedModality practicedModalities) {
        this.practicedModalities.remove(practicedModalities);
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

    public Set<Echelon> getEchelons() {
        return echelons;
    }

    public void setEchelons(Set<Echelon> echelons) {
        this.echelons = echelons;
    }

    public void addEchelon(Echelon echelon) {
        this.echelons.add(echelon);
    }

    public void removeEchelon(Echelon echelon) {
        this.echelons.remove(echelon);
    }

    public Set<Graduations> getGraduations() {
        return graduations;
    }

    public void setGraduations(Set<Graduations> graduations) {
        this.graduations = graduations;
    }

    public void addGraduation(Graduations graduation) {
        this.graduations.add(graduation);
    }

    public void removeGraduation(Graduations graduation) {
        this.graduations.remove(graduation);
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

    public int getSportYear() {
        return sportYear;
    }

    public void setSportYear(int sportYear) {
        this.sportYear = sportYear;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Athlete> getModalityAthletes() {
        Set<Athlete> athletes = new LinkedHashSet<>();
        for (PracticedModality practicedModality : practicedModalities) {
            if (practicedModality.getAthlete()!=null)
                athletes.add(practicedModality.getAthlete());
        }
        return athletes;
    }

    public Set<Coach> getModalityCoaches() {
        Set<Coach> coaches = new LinkedHashSet<>();
        for (TeachedModality teachedModality : teachedModalities) {
            if (teachedModality.getCoach()!=null)
                coaches.add(teachedModality.getCoach());
        }
        return coaches;
    }
}
