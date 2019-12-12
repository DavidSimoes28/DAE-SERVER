package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Class implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Coach coach;
    @ManyToOne
    private Schedule schedule;
    @ManyToMany
    private Set<Athlete> presences;
}
