package dtos;

import java.io.Serializable;
import java.util.List;

public class EmailDTO implements Serializable {
    private String subject;
    private String message;
    private List<UserDTO> users;

    public EmailDTO() {
    }

    public EmailDTO(String subject, String message, List<UserDTO> users) {
        this.subject = subject;
        this.message = message;
        this.users = users;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
