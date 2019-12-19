package entities;

import com.sun.istack.Nullable;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SUBSCRIPTIONS")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Athlete athlete;
    @ManyToOne
    private Modality modality;
    @ManyToOne
    private Schedule schedule;
    @ManyToOne
    @Nullable
    private Echelon echelon;
    @ManyToOne
    @Nullable
    private Graduations graduations;
    private Date subscription_date;
    private Double subscription_price;

    public Subscription() {
    }

    public Subscription(Athlete athlete, Modality modality, Schedule schedule, Graduations graduations, Date subscription_date, Double subscription_price) {
        this.athlete = athlete;
        this.modality = modality;
        this.schedule = schedule;
        this.graduations = graduations;
        this.subscription_date = subscription_date;
        this.subscription_price = subscription_price;
    }

    public Subscription(Athlete athlete, Modality modality, Schedule schedule, Echelon echelon, Date subscription_date, Double subscription_price) {
        this.athlete = athlete;
        this.modality = modality;
        this.schedule = schedule;
        this.echelon = echelon;
        this.subscription_date = subscription_date;
        this.subscription_price = subscription_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public Modality getModality() {
        return modality;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Echelon getEchelon() {
        return echelon;
    }

    public void setEchelon(Echelon echelon) {
        this.echelon = echelon;
    }

    public Graduations getGraduations() {
        return graduations;
    }

    public void setGraduations(Graduations graduations) {
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
