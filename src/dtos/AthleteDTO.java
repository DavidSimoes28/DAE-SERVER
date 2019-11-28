package dtos;

import java.util.LinkedHashSet;
import java.util.Set;

public class AthleteDTO extends PartnerDTO {
    private Set<EchelonDTO> echelons;
    private int modalityID;


    public AthleteDTO() {
        echelons = new LinkedHashSet<>();
    }

    public AthleteDTO(String username, String password, String name, String email) {
        super(username, password, name, email);
        //this.modalityID = modalityID;
        echelons = new LinkedHashSet<>();
    }

    public Set<EchelonDTO> getEchelons() {
        return echelons;
    }

    public void setEchelons(Set<EchelonDTO> echelons) {
        this.echelons = echelons;
    }
}
