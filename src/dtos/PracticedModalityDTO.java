package dtos;

import java.util.Set;

public class PracticedModalityDTO {

    private int id;
    private ModalityDTO modality;
    private EchelonDTO echelon;
    private GraduationsDTO graduations;
    private AthleteDTO athlete;
    private Set<ScheduleDTO> schedules;

    public PracticedModalityDTO() {
    }

    public PracticedModalityDTO(int id, ModalityDTO modality, EchelonDTO echelon, GraduationsDTO graduations, AthleteDTO athlete) {
        this.id = id;
        this.modality = modality;
        this.echelon = echelon;
        this.graduations = graduations;
        this.athlete = athlete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModalityDTO getModality() {
        return modality;
    }

    public void setModality(ModalityDTO modality) {
        this.modality = modality;
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

    public AthleteDTO getAthlete() {
        return athlete;
    }

    public void setAthlete(AthleteDTO athlete) {
        this.athlete = athlete;
    }

    public Set<ScheduleDTO> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<ScheduleDTO> schedules) {
        this.schedules = schedules;
    }
}
