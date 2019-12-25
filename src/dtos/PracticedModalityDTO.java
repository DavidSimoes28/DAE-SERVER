package dtos;

import java.util.List;

public class PracticedModalityDTO {

    private int id;
    private int modalityId;
    private int echelonId;
    private int graduationsId;
    private String athleteUsername;
    private List<ScheduleDTO> schedules;

    public PracticedModalityDTO() {
    }

    public PracticedModalityDTO(int id, int modalityId, int echelonId, int graduationsId, String athleteUsername) {
        this.id = id;
        this.modalityId = modalityId;
        this.echelonId = echelonId;
        this.graduationsId = graduationsId;
        this.athleteUsername = athleteUsername;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModalityId() {
        return modalityId;
    }

    public void setModalityId(int modalityId) {
        this.modalityId = modalityId;
    }

    public int getEchelonId() {
        return echelonId;
    }

    public void setEchelonId(int echelonId) {
        this.echelonId = echelonId;
    }

    public int getGraduationsId() {
        return graduationsId;
    }

    public void setGraduationsId(int graduationsId) {
        this.graduationsId = graduationsId;
    }

    public String getAthleteUsername() {
        return athleteUsername;
    }

    public void setAthleteUsername(String athleteUsername) {
        this.athleteUsername = athleteUsername;
    }

    public List<ScheduleDTO> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleDTO> schedules) {
        this.schedules = schedules;
    }
}
