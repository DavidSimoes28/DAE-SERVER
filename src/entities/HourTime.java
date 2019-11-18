package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "HOURTIME")
public class HourTime implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int hour;
    private int minutes;

    public HourTime() {
    }

    public HourTime(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
