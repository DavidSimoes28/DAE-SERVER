package dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ClassesDTO implements Serializable {
    private int id;
    private CoachDTO coach;
    private ScheduleDTO schedule;
    private List<AthleteDTO> athletesPresent;
    private ModalityDTO modality;
    private Date date;

    public ClassesDTO() {
    }

    public ClassesDTO(int id, CoachDTO coach, ScheduleDTO schedule, ModalityDTO modality, Date date) {
        this.id = id;
        this.coach = coach;
        this.schedule = schedule;
        this.modality = modality;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CoachDTO getCoach() {
        return coach;
    }

    public void setCoach(CoachDTO coach) {
        this.coach = coach;
    }

    public ScheduleDTO getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleDTO schedule) {
        this.schedule = schedule;
    }

    public List<AthleteDTO> getAthletesPresent() {
        return athletesPresent;
    }

    public void setAthletesPresent(List<AthleteDTO> athletesPresent) {
        this.athletesPresent = athletesPresent;
    }

    public ModalityDTO getModality() {
        return modality;
    }

    public void setModality(ModalityDTO modality) {
        this.modality = modality;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
