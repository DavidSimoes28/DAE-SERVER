package dtos;

import java.io.Serializable;

public class HourTimeDTO implements Serializable {
    private int id;
    private int hour;
    private int minutes;

    public HourTimeDTO() {
    }

    public HourTimeDTO(int id, int hour, int minutes) {
        this.id = id;
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
