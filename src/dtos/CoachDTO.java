package dtos;

import java.util.LinkedHashSet;
import java.util.Set;

public class CoachDTO extends UserDTO {
    private Set<ModalityDTO> modalities;
    private Set<EchelonDTO> echelons;

    public CoachDTO() {
        echelons = new LinkedHashSet<>();
        modalities = new LinkedHashSet<>();
    }

    public CoachDTO(String username, String password, String name, String email) {
        super(username, password, name, email);
        echelons = new LinkedHashSet<>();
        modalities = new LinkedHashSet<>();
    }

    public Set<ModalityDTO> getModalities() {
        return modalities;
    }

    public void setModalities(Set<ModalityDTO> modalities) {
        this.modalities = modalities;
    }

    public Set<EchelonDTO> getEchelons() {
        return echelons;
    }

    public void setEchelons(Set<EchelonDTO> echelons) {
        this.echelons = echelons;
    }
}
