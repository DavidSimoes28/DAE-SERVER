package dtos;

import java.io.Serializable;
import java.util.List;

public class TeachedModalityDTO implements Serializable {
    private int id;
    private ModalityDTO modality;
    private CoachDTO coach;
    private List<ScheduleDTO> schedules;

    public TeachedModalityDTO() {
    }

    public TeachedModalityDTO(int id, ModalityDTO modality, CoachDTO coach) {
        this.id = id;
        this.modality = modality;
        this.coach = coach;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModalityDTO getModality() {
        return modality;
    }

    public void setModality(ModalityDTO modality) {
        this.modality = modality;
    }

    public CoachDTO getCoach() {
        return coach;
    }

    public void setCoach(CoachDTO coach) {
        this.coach = coach;
    }

    public List<ScheduleDTO> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleDTO> schedules) {
        this.schedules = schedules;
    }
}
