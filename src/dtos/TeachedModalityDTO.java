package dtos;

import java.io.Serializable;
import java.util.List;

public class TeachedModalityDTO implements Serializable {
    private int id;
    private int modalityId;
    private String coachUsername;
    private List<ScheduleDTO> schedules;

    public TeachedModalityDTO() {
    }

    public TeachedModalityDTO(int id, int modalityId, String coachUsername) {
        this.id = id;
        this.modalityId = modalityId;
        this.coachUsername = coachUsername;
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

    public String getCoachUsername() {
        return coachUsername;
    }

    public void setCoachUsername(String coachUsername) {
        this.coachUsername = coachUsername;
    }

    public List<ScheduleDTO> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleDTO> schedules) {
        this.schedules = schedules;
    }
}
