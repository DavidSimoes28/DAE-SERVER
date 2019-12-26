package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@NamedQueries({
        @NamedQuery(
                name = "getAllStates",
                query = "SELECT s FROM State s ORDER BY s.name"
        )
})
@Entity
@Table(name = "STATES")
public class State implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @OneToMany(mappedBy = "state", cascade = CascadeType.REMOVE)
    private Set<Payment> payments;

    public State() {
        payments = new LinkedHashSet<>();
    }

    public State(String state) {
        this.name = state;
        payments = new LinkedHashSet<>();
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

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
    }

    public void removePayment(Payment payment) {
        this.payments.remove(payment);
    }
}
