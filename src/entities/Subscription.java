package entities;

import com.sun.istack.Nullable;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllSubscriptions",
                query = "SELECT a FROM Subscription a ORDER BY a.id"
        )
})

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
    private Date subscriptionDate;
    private Double subscriptionPrice;

    public Subscription() {
    }

    public Subscription(Athlete athlete, Modality modality, Schedule schedule, Graduations graduations, Date subscriptionDate, Double subscriptionPrice) {
        this.athlete = athlete;
        this.modality = modality;
        this.schedule = schedule;
        this.graduations = graduations;
        this.subscriptionDate = subscriptionDate;
        this.subscriptionPrice = subscriptionPrice;
    }

    public Subscription(Athlete athlete, Modality modality, Schedule schedule, Echelon echelon, Date subscriptionDate, Double subscriptionPrice) {
        this.athlete = athlete;
        this.modality = modality;
        this.schedule = schedule;
        this.echelon = echelon;
        this.subscriptionDate = subscriptionDate;
        this.subscriptionPrice = subscriptionPrice;
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
