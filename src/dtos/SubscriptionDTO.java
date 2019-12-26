package dtos;

import java.io.Serializable;
import java.util.Date;

public class SubscriptionDTO implements Serializable {
    private int id;
    private String athleteUsername;
    private int modalityId;
    private int scheduleId;
    private int echelonId;
    private int graduationsId;
    private Date subscriptionDate;
    private Double subscriptionPrice;

    public SubscriptionDTO() {
    }

    public SubscriptionDTO(int id, String athleteUsername, int modalityId, int scheduleId, int echelonId, int graduationsId, Date subscriptionDate, Double subscriptionPrice) {
        this.id = id;
        this.athleteUsername = athleteUsername;
        this.modalityId = modalityId;
        this.scheduleId = scheduleId;
        this.echelonId = echelonId;
        this.graduationsId = graduationsId;
        this.subscriptionDate = subscriptionDate;
        this.subscriptionPrice = subscriptionPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAthleteUsername() {
        return athleteUsername;
    }

    public void setAthleteUsername(String athleteUsername) {
        this.athleteUsername = athleteUsername;
    }

    public int getModalityId() {
        return modalityId;
    }

    public void setModalityId(int modalityId) {
        this.modalityId = modalityId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
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

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Double getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void setSubscriptionPrice(Double subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }
}
