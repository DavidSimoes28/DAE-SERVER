package dtos;

public class FilterUserDTO {
    private String username;

    public FilterUserDTO() {
    }

    public FilterUserDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
