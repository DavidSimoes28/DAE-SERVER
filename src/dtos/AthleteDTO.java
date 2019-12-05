package dtos;

import entities.Graduations;

import java.util.LinkedHashSet;
import java.util.Set;

public class AthleteDTO extends PartnerDTO {
    private Set<EchelonDTO> echelons;
    private Set<GraduationsDTO> graduations;
    private Set<ModalityDTO> modalities;


    public AthleteDTO() {
        echelons = new LinkedHashSet<>();
        graduations = new LinkedHashSet<>();
        modalities = new LinkedHashSet<>();
    }

    public AthleteDTO(String username, String password, String name, String email) {
        super(username, password, name, email);
        //this.modalityID = modalityID;
        echelons = new LinkedHashSet<>();
        graduations = new LinkedHashSet<>();
        modalities = new LinkedHashSet<>();
    }

    public Set<EchelonDTO> getEchelons() {
        return echelons;
    }

    public void setEchelons(Set<EchelonDTO> echelons) {
        this.echelons = echelons;
    }

    public Set<GraduationsDTO> getGraduations() {
        return graduations;
    }

    public void setGraduations(Set<GraduationsDTO> graduations) {
        this.graduations = graduations;
    }

    public Set<ModalityDTO> getModalities() {
        return modalities;
    }

    public void setModalities(Set<ModalityDTO> modalities) {
        this.modalities = modalities;
    }

}
