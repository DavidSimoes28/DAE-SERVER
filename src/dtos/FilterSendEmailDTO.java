package dtos;

public class FilterSendEmailDTO {
    private int modalityId;
    private int echelonId;

    public FilterSendEmailDTO() {
    }

    public FilterSendEmailDTO(int modalityId, int echelonId) {
        this.modalityId = modalityId;
        this.echelonId = echelonId;
    }

    public int getModalityId() {
        return modalityId;
    }

    public void setModalityId(int modalityId) {
        this.modalityId = modalityId;
    }

    public int getEchelonId() {
        return echelonId;
    }

    public void setEchelonId(int echelonId) {
        this.echelonId = echelonId;
    }
}
