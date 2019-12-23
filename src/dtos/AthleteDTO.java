package dtos;

import java.util.ArrayList;
import java.util.List;

public class AthleteDTO extends PartnerDTO {
    private List<PracticedModalityDTO> modalities;
    private List<ClassesDTO> classes;


    public AthleteDTO() {
        classes = new ArrayList<>();
        modalities = new ArrayList<>();
    }

    public AthleteDTO(String username, String password, String name, String email) {
        super(username, password, name, email);
        classes = new ArrayList<>();
        modalities = new ArrayList<>();
    }

    public List<PracticedModalityDTO> getModalities() {
        return modalities;
    }

    public void setModalities(List<PracticedModalityDTO> modalities) {
        this.modalities = modalities;
    }

    public List<ClassesDTO> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassesDTO> classes) {
        this.classes = classes;
    }
}
