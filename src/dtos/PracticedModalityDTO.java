package dtos;

import java.util.Set;

public class PracticedModalityDTO {

    int id;
    private int modality;
    private int echelon;
    private int graduations;
    private String athlete;
    private Set<ScheduleDTO> schedules;

    public PracticedModalityDTO() {
    }

    public PracticedModalityDTO(int id, int modality, int echelon, int graduations, String athlete) {
        this.id = id;
        this.modality = modality;
        this.echelon = echelon;
        this.graduations = graduations;
        this.athlete = athlete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModality() {
        return modality;
    }

    public void setModality(int modality) {
        this.modality = modality;
    }

    public int getEchelon() {
        return echelon;
    }

    public void setEchelon(int echelon) {
        this.echelon = echelon;
    }

    public int getGraduations() {
        return graduations;
    }

    public void setGraduations(int graduations) {
        this.graduations = graduations;
    }

    public String getAthlete() {
        return athlete;
    }

    public void setAthlete(String athlete) {
        this.athlete = athlete;
    }

    public Set<ScheduleDTO> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<ScheduleDTO> schedules) {
        this.schedules = schedules;
    }
}
