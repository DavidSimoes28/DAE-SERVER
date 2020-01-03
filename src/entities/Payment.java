package entities;

import javax.persistence.*;
import java.io.Serializable;

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
    @ManyToOne
    private State state;
    @ManyToOne
    private Receipt receipt;
    private Double valueInEur;
    @Version
    private int version;

    public Payment() {
    }

    public Payment(State state, Purchase purchase,Double valueInEur) {
        this.state = state;
        this.purchase = purchase;
        this.valueInEur = valueInEur;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public Double getValueInEur() {
        return valueInEur;
    }

    public void setValueInEur(Double valueInEur) {
        this.valueInEur = valueInEur;
    }
}
