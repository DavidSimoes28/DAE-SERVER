package dtos;

public class GraduationsDTO {
    private int id;
    private String code;
    private String name;
    private int minimumAge;
    private ModalityDTO modalityDTO;

    public GraduationsDTO() {
    }

    public GraduationsDTO(int id, String code, String name, int minimumAge, ModalityDTO modalityDTO) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.minimumAge = minimumAge;
        this.modalityDTO = modalityDTO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public ModalityDTO getModalityDTO() {
        return modalityDTO;
    }

    public void setModalityDTO(ModalityDTO modalityDTO) {
        this.modalityDTO = modalityDTO;
    }
}
