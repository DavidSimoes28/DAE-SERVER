package entities;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries({
        @NamedQuery(
                name = "getAllGraduations",
                query = "SELECT a FROM Graduations a ORDER BY a.id"
        )
})
@Entity
@Table(name = "GRADUATIONS")
public class Graduations implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    public Graduations(String name) {
        this.name = name;
    }

    public Graduations(){
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
