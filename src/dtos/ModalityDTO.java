package dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ModalityDTO implements Serializable {
    private int id;
    private String name;
    private List<PracticedModalityDTO> practicedModalities;
    private List<EchelonDTO> echelons;
    private List<GraduationsDTO> graduations;
    private List<TeachedModalityDTO> teachedModalities;
    private List<ScheduleDTO> scheduleDTOS;
    private int sportYear;
    private boolean active;

    public ModalityDTO() {
        scheduleDTOS = new ArrayList<>();
        echelons = new ArrayList<>();
        graduations = new ArrayList<>();
        teachedModalities = new ArrayList<>();
        practicedModalities = new ArrayList<>();
    }

    public ModalityDTO(int id, String name, int sportYear, boolean active) {
        this.id = id;
        this.name = name;
        this.sportYear = sportYear;
        this.active = active;
        scheduleDTOS = new ArrayList<>();
        echelons = new ArrayList<>();
        graduations = new ArrayList<>();
        teachedModalities = new ArrayList<>();
        practicedModalities = new ArrayList<>();
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

    public List<ScheduleDTO> getScheduleDTOS() {
        return scheduleDTOS;
    }

    public void setScheduleDTOS(List<ScheduleDTO> scheduleDTOS) {
        this.scheduleDTOS = scheduleDTOS;
    }

    public List<PracticedModalityDTO> getPracticedModalities() {
        return practicedModalities;
    }

    public void setPracticedModalities(List<PracticedModalityDTO> practicedModalities) {
        this.practicedModalities = practicedModalities;
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

    public List<TeachedModalityDTO> getTeachedModalities() {
        return teachedModalities;
    }

    public void setTeachedModalities(List<TeachedModalityDTO> teachedModalities) {
        this.teachedModalities = teachedModalities;
    }
}
