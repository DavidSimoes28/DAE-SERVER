package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPayments",
                query = "SELECT s FROM Payment s ORDER BY s.id"
        )
})
@Table(name = "PAYMENTS")
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Purchase purchase;
    private int quantity;
    @ManyToOne
    private State state;

    public Payment() {
    }

    public Payment(int quantity, State state, Purchase purchase) {
        this.quantity = quantity;
        this.state = state;
        this.purchase = purchase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
