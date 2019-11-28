package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@NamedQueries({
        @NamedQuery(
                name = "getAllStates",
                query = "SELECT s FROM State s ORDER BY s.state"
        )
})
@Entity
@Table(name = "STATES")
public class State implements Serializable {
    @Id
    private String state;
    @OneToMany(mappedBy = "state", cascade = CascadeType.REMOVE)
    private Set<Payment> payments;

    public State() {
        payments = new LinkedHashSet<>();
    }

    public State(String state) {
        this.state = state;
        payments = new LinkedHashSet<>();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
