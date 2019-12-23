package dtos;

public class EchelonDTO {
    private int id;
    private String name;
    private int initialAge;
    private int finalAge;
    private ModalityDTO modalityDTO;

    public EchelonDTO() {
    }

    public EchelonDTO(int id, String name, int initialAge, int finalAge, ModalityDTO modalityDTO) {
        this.id = id;
        this.name = name;
        this.initialAge = initialAge;
        this.finalAge = finalAge;
        this.modalityDTO = modalityDTO;
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

    public ModalityDTO getModalityDTO() {
        return modalityDTO;
    }

    public void setModalityDTO(ModalityDTO modalityDTO) {
        this.modalityDTO = modalityDTO;
    }
}
