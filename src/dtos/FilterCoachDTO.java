package dtos;

public class FilterCoachDTO {
    private String username;
    private int modalityId;

    public FilterCoachDTO() {
    }

    public FilterCoachDTO(String username, int modalityId) {
        this.username = username;
        this.modalityId = modalityId;
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
}
