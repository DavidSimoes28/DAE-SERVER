package dtos;

import java.util.ArrayList;
import java.util.List;

public class AthleteDTO extends PartnerDTO {
    private List<ModalityDTO> modalities;
    private List<EchelonDTO> echelons;
    private List<GraduationsDTO> graduations;
    private List<ClassesDTO> classes;


    public AthleteDTO() {
        echelons = new ArrayList<>();
        graduations = new ArrayList<>();
        classes = new ArrayList<>();
        modalities = new ArrayList<>();
    }

    public AthleteDTO(String username, String password, String name, String email) {
        super(username, password, name, email);
        classes = new ArrayList<>();
        modalities = new ArrayList<>();
        echelons = new ArrayList<>();
        graduations = new ArrayList<>();
    }

    public List<ModalityDTO> getModalities() {
        return modalities;
    }

    public void setModalities(List<ModalityDTO> modalities) {
        this.modalities = modalities;
    }

    public List<ClassesDTO> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassesDTO> classes) {
        this.classes = classes;
    }

    public List<EchelonDTO> getEchelons() {
        return echelons;
    }

    public void setEchelons(List<EchelonDTO> echelons) {
        this.echelons = echelons;
    }

    public List<GraduationsDTO> getGraduations() {
        return graduations;
    }

    public void setGraduations(List<GraduationsDTO> graduations) {
        this.graduations = graduations;
    }
}
