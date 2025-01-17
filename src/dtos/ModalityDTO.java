package dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ModalityDTO implements Serializable {
    private int id;
    private String name;
    private List<AthleteDTO> athletes;
    private List<EchelonDTO> echelons;
    private List<GraduationsDTO> graduations;
    private List<CoachDTO> coaches;
    private List<ScheduleDTO> schedules;
    private int sportYear;
    private boolean active;

    public ModalityDTO() {
        schedules= new ArrayList<>();
        echelons = new ArrayList<>();
        graduations = new ArrayList<>();
        athletes = new ArrayList<>();
        coaches = new ArrayList<>();
    }

    public ModalityDTO(int id, String name, int sportYear, boolean active) {
        this.id = id;
        this.name = name;
        this.sportYear = sportYear;
        this.active = active;
        schedules = new ArrayList<>();
        echelons = new ArrayList<>();
        graduations = new ArrayList<>();
        athletes = new ArrayList<>();
        coaches = new ArrayList<>();
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

    public List<ScheduleDTO> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleDTO> schedules) {
        this.schedules = schedules;
    }

    public List<EchelonDTO> getEchelons() {
        return echelons;
    }

    public void setEchelons(List<EchelonDTO> echelons) {
        this.echelons = echelons;
    }

    public List<GraduationsDTO> getGraduations() {
        return graduations;
    }

    public void setGraduations(List<GraduationsDTO> graduations) {
        this.graduations = graduations;
    }

    public List<AthleteDTO> getAthletes() {
        return athletes;
    }

    public void setAthletes(List<AthleteDTO> athletes) {
        this.athletes = athletes;
    }

    public List<CoachDTO> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<CoachDTO> coaches) {
        this.coaches = coaches;
    }
}
