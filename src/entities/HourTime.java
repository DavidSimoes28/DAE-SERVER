package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "HOURTIME")
public class HourTime implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int hour;
    private int minutes;
    @OneToMany(mappedBy = "startDate")
    private Set<Schedule> schedulesStart;
    @OneToMany(mappedBy = "endDate")
    private Set<Schedule> schedulesEnd;

    public HourTime() {
        this.schedulesStart = new LinkedHashSet<>();
        this.schedulesEnd = new LinkedHashSet<>();
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

    public Set<Schedule> getSchedulesStart() {
        return schedulesStart;
    }

    public void setSchedulesStart(Set<Schedule> schedulesStart) {
        this.schedulesStart = schedulesStart;
    }

    public Set<Schedule> getSchedulesEnd() {
        return schedulesEnd;
    }

    public void setSchedulesEnd(Set<Schedule> schedulesEnd) {
        this.schedulesEnd = schedulesEnd;
    }
}
