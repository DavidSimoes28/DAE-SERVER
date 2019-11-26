package dtos;

public class AdministratorDTO extends UserDTO{

    public AdministratorDTO() {
    }

    public AdministratorDTO(String username, String password, String name, String email) {
        super(username,password,name,email);
    }
}