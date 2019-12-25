package dtos;

public class GraduationsDTO {
    private int id;
    private String name;
    private int minimumAge;
    private int modalityId;

    public GraduationsDTO() {
    }

    public GraduationsDTO(int id, String name, int minimumAge, int modalityId) {
        this.id = id;
        this.name = name;
        this.minimumAge = minimumAge;
        this.modalityId = modalityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(int minimumAge) {
        this.minimumAge = minimumAge;
    }

    public int getModalityId() {
        return modalityId;
    }

    public void setModalityId(int modalityId) {
        this.modalityId = modalityId;
    }
}
