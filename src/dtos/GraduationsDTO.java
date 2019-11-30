package dtos;

public class GraduationsDTO {
    private String code;
    private String name;
    private int minimumAge;

    public GraduationsDTO() {
    }

    public GraduationsDTO(String code, String name, int minimumAge) {
        this.code = code;
        this.name = name;
        this.minimumAge = minimumAge;
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
}
