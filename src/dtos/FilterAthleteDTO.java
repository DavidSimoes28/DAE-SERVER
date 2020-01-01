package dtos;

public class FilterAthleteDTO {
    private String username;
    private int modalityId;
    private int graduationId;
    private int echelonId;

    public FilterAthleteDTO() {
    }

    public FilterAthleteDTO(String username, int modalityId, int graduationId, int echelonId) {
        this.username = username;
        this.modalityId = modalityId;
        this.graduationId = graduationId;
        this.echelonId = echelonId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getModalityId() {
        return modalityId;
    }

    public void setModalityId(int modalityId) {
        this.modalityId = modalityId;
    }

    public int getGraduationId() {
        return graduationId;
    }

    public void setGraduationId(int graduationId) {
        this.graduationId = graduationId;
    }

    public int getEchelonId() {
        return echelonId;
    }

    public void setEchelonId(int echelonId) {
        this.echelonId = echelonId;
    }
}
