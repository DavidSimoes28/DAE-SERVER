package dtos;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class ModalityDTO implements Serializable {
    private int id;
    private String name;
    private int scheduleId;
    private Set<CoachDTO> coaches;
    private Set<PartnerDTO> athletes;

    public ModalityDTO() {
        coaches = new LinkedHashSet<>();
        athletes = new LinkedHashSet<>();
    }

    public ModalityDTO(int id, String name) {
        this.id = id;
        this.name = name;
        coaches = new LinkedHashSet<>();
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

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Set<CoachDTO> getCoaches() {
        return coaches;
    }

    public void setCoaches(Set<CoachDTO> coaches) {
        this.coaches = coaches;
    }

    public Set<PartnerDTO> getAthletes() {
        return athletes;
    }

    public void setAthletes(Set<PartnerDTO> athletes) {
        this.athletes = athletes;
    }
}
