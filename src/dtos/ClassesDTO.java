package dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ClassesDTO implements Serializable {
    private int id;
    private String coachUsername;
    private int scheduleID;
    private List<AthleteDTO> athletesPresent;
    private int modalityID;
    private String date;

    public ClassesDTO() {
    }

    public ClassesDTO(int id, String coachUsername, int scheduleID, int modalityID, String date) {
        this.id = id;
        this.coachUsername = coachUsername;
        this.scheduleID = scheduleID;
        this.modalityID = modalityID;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoachUsername() {
        return coachUsername;
    }

    public void setCoachUsername(String coachUsername) {
        this.coachUsername = coachUsername;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public List<AthleteDTO> getAthletesPresent() {
        return athletesPresent;
    }

    public void setAthletesPresent(List<AthleteDTO> athletesPresent) {
        this.athletesPresent = athletesPresent;
    }

    public int getModalityID() {
        return modalityID;
    }

    public void setModalityID(int modalityID) {
        this.modalityID = modalityID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
