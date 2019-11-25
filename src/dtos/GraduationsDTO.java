package dtos;

public class GraduationsDTO {
    private int id;
    private String name;

    public GraduationsDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public GraduationsDTO() {
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
}
