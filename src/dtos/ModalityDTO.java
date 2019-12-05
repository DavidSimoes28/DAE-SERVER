package dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ModalityDTO implements Serializable {
    private int id;
    private String name;
    private int scheduleId;
    private List<CoachDTO> coaches;
    private List<AthleteDTO> athletes;

    public ModalityDTO() {
        coaches = new ArrayList<>();
        athletes = new ArrayList<>();
    }

    public ModalityDTO(int id, String name) {
        this.id = id;
        this.name = name;
        coaches = new ArrayList<>();
        athletes = new ArrayList<>();
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

    public List<CoachDTO> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<CoachDTO> coaches) {
        this.coaches = coaches;
    }

    public List<AthleteDTO> getAthletes() {
        return athletes;
    }

    public void setAthletes(List<AthleteDTO> athletes) {
        this.athletes = athletes;
    }
}
