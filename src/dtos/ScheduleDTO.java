package dtos;

import java.io.Serializable;

public class ScheduleDTO implements Serializable {
    private int id;
    private int dayOfWeek;
    private HourTimeDTO startDate;
    private HourTimeDTO endDate;

    public ScheduleDTO() {
    }

    public ScheduleDTO(int id, int dayOfWeek, HourTimeDTO startDate, HourTimeDTO endDate) {
        this.id = id;
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

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public HourTimeDTO getStartDate() {
        return startDate;
    }

    public void setStartDate(HourTimeDTO startDate) {
        this.startDate = startDate;
    }

    public HourTimeDTO getEndDate() {
        return endDate;
    }

    public void setEndDate(HourTimeDTO endDate) {
        this.endDate = endDate;
    }
}
