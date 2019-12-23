package dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ModalityDTO implements Serializable {
    private int id;
    private String name;
    private List<ScheduleDTO> scheduleDTOS;
    private int sportYear;
    private boolean active;

    public ModalityDTO() {
        scheduleDTOS = new ArrayList<>();
    }

    public ModalityDTO(int id, String name, int sportYear, boolean active) {
        this.id = id;
        this.name = name;
        this.sportYear = sportYear;
        this.active = active;
        scheduleDTOS = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSportYear() {
        return sportYear;
    }

    public void setSportYear(int sportYear) {
        this.sportYear = sportYear;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<ScheduleDTO> getScheduleDTOS() {
        return scheduleDTOS;
    }

    public void setScheduleDTOS(List<ScheduleDTO> scheduleDTOS) {
        this.scheduleDTOS = scheduleDTOS;
    }
}
