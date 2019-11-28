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
    @ManyToMany(mappedBy = "payments", fetch = FetchType.EAGER)
    @JoinTable(name = "PAYMENTS_PURCHASE",
            joinColumns = @JoinColumn(name = "PAYMENT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PURCHASE_ID", referencedColumnName = "ID"))
    private Set<Purchase> purchases;
    private int quantity;
    @ManyToOne
    private State state;

    public Payment() {
    }

    public Payment(int quantity, State state) {
        this.quantity = quantity;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }

    public void removePurchase(Purchase purchase) {
        this.purchases.remove(purchase);
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
