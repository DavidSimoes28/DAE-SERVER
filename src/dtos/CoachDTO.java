package dtos;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CoachDTO extends UserDTO {
    private List<ModalityDTO> modalities;
    private List<EchelonDTO> echelons;
    private List<ClassesDTO> classes;

    public CoachDTO() {
        echelons = new ArrayList<>();
        modalities = new ArrayList<>();
        classes = new ArrayList<>();
    }

    public CoachDTO(String username, String password, String name, String email) {
        super(username, password, name, email);
        echelons = new ArrayList<>();
        modalities = new ArrayList<>();
        classes = new ArrayList<>();
    }

    public List<ModalityDTO> getModalities() {
        return modalities;
    }

    public void setModalities(List<ModalityDTO> modalities) {
        this.modalities = modalities;
    }

    public List<EchelonDTO> getEchelons() {
        return echelons;
    }

    public void setEchelons(List<EchelonDTO> echelons) {
        this.echelons = echelons;
    }

    public List<ClassesDTO> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassesDTO> classes) {
        this.classes = classes;
    }
}
