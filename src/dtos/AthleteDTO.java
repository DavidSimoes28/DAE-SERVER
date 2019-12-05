package dtos;

import entities.Graduations;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AthleteDTO extends PartnerDTO {
    private List<EchelonDTO> echelons;
    private List<GraduationsDTO> graduations;
    private List<ModalityDTO> modalities;


    public AthleteDTO() {
        echelons = new ArrayList<>();
        graduations = new ArrayList<>();
        modalities = new ArrayList<>();
    }

    public AthleteDTO(String username, String password, String name, String email) {
        super(username, password, name, email);
        //this.modalityID = modalityID;
        echelons = new ArrayList<>();
        graduations = new ArrayList<>();
        modalities = new ArrayList<>();
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

    public List<ModalityDTO> getModalities() {
        return modalities;
    }

    public void setModalities(List<ModalityDTO> modalities) {
        this.modalities = modalities;
    }

}
