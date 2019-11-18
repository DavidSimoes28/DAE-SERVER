package dtos;

import java.io.Serializable;
import java.util.Set;

public class ModalityDTO implements Serializable {
    private int id;
    private String name;
    private int scheduleId;
    private Set<CoachDTO> coaches;
    private Set<PartnerOrAthleteDTO> athletes;

    public ModalityDTO() {
    }

    public ModalityDTO(int id, String name, int scheduleId) {
        this.id = id;
        this.name = name;
        this.scheduleId = scheduleId;
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

    public Set<PartnerOrAthleteDTO> getAthletes() {
        return athletes;
    }

    public void setAthletes(Set<PartnerOrAthleteDTO> athletes) {
        this.athletes = athletes;
    }
}
