package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;

@Entity
@Table(name = "SCHEDULE")
public class Schedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private DayOfWeek dayOfWeek;
    @OneToOne
    private HourTime startDate;
    @OneToOne
    private HourTime endDate;

    public Schedule() {
    }

    public Schedule(DayOfWeek dayOfWeek, HourTime startDate, HourTime endDate) {
        this.dayOfWeek = dayOfWeek;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DayOfWeek getDayOfTheWeek() {
        return dayOfWeek;
    }

    public void setDayOfTheWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public HourTime getStartDate() {
        return startDate;
    }

    public void setStartDate(HourTime startDate) {
        this.startDate = startDate;
    }

    public HourTime getEndDate() {
        return endDate;
    }

    public void setEndDate(HourTime endDate) {
        this.endDate = endDate;
    }
}
