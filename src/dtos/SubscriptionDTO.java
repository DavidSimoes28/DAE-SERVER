package dtos;

import java.io.Serializable;
import java.util.Date;

public class SubscriptionDTO implements Serializable {
    private int id;
    private AthleteDTO athlete;
    private ModalityDTO modality;
    private ScheduleDTO schedule;
    private EchelonDTO echelon;
    private GraduationsDTO graduations;
    private Date subscription_date;
    private Double subscription_price;

    public SubscriptionDTO() {
    }

    public SubscriptionDTO(int id, AthleteDTO athlete, ModalityDTO modality, ScheduleDTO schedule, EchelonDTO echelon, GraduationsDTO graduations, Date subscription_date, Double subscription_price) {
        this.id = id;
        this.athlete = athlete;
        this.modality = modality;
        this.schedule = schedule;
        this.echelon = echelon;
        this.graduations = graduations;
        this.subscription_date = subscription_date;
        this.subscription_price = subscription_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AthleteDTO getAthlete() {
        return athlete;
    }

    public void setAthlete(AthleteDTO athlete) {
        this.athlete = athlete;
    }

    public ModalityDTO getModality() {
        return modality;
    }

    public void setModality(ModalityDTO modality) {
        this.modality = modality;
    }

    public ScheduleDTO getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleDTO schedule) {
        this.schedule = schedule;
    }

    public EchelonDTO getEchelon() {
        return echelon;
    }

    public void setEchelon(EchelonDTO echelon) {
        this.echelon = echelon;
    }

    public GraduationsDTO getGraduations() {
        return graduations;
    }

    public void setGraduations(GraduationsDTO graduations) {
        this.graduations = graduations;
    }

    public Date getSubscription_date() {
        return subscription_date;
    }

    public void setSubscription_date(Date subscription_date) {
        this.subscription_date = subscription_date;
    }

    public Double getSubscription_price() {
        return subscription_price;
    }

    public void setSubscription_price(Double subscription_price) {
        this.subscription_price = subscription_price;
    }
}
