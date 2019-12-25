package dtos;

public class EchelonDTO {
    private int id;
    private String name;
    private int initialAge;
    private int finalAge;
    private int modalityId;

    public EchelonDTO() {
    }

    public EchelonDTO(int id, String name, int initialAge, int finalAge, int modalityId) {
        this.id = id;
        this.name = name;
        this.initialAge = initialAge;
        this.finalAge = finalAge;
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

    public int getInitialAge() {
        return initialAge;
    }

    public void setInitialAge(int initialAge) {
        this.initialAge = initialAge;
    }

    public int getFinalAge() {
        return finalAge;
    }

    public void setFinalAge(int finalAge) {
        this.finalAge = finalAge;
    }

    public int getModalityId() {
        return modalityId;
    }

    public void setModalityId(int modalityId) {
        this.modalityId = modalityId;
    }
}
