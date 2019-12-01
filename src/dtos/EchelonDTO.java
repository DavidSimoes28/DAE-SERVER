package dtos;

public class EchelonDTO {
    private String name;
    private int initialAge;
    private int finalAge;

    public EchelonDTO() {
    }

    public EchelonDTO(String name, int initialAge, int finalAge) {
        this.name = name;
        this.initialAge = initialAge;
        this.finalAge = finalAge;
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
}
