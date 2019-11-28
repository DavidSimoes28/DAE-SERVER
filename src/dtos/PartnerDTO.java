package dtos;

import java.util.LinkedHashSet;
import java.util.Set;

public class PartnerDTO extends UserDTO {
    private Set<ModalityDTO> modalities;

    public PartnerDTO() {
        modalities = new LinkedHashSet<>();
    }

    public PartnerDTO(String username, String password, String name, String email) {
        super(username, password, name, email);
        modalities = new LinkedHashSet<>();
    }

    public Set<ModalityDTO> getModalities() {
        return modalities;
    }

    public void setModalities(Set<ModalityDTO> modalities) {
        this.modalities = modalities;
    }

}
