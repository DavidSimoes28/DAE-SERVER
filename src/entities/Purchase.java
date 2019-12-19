package entities;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
@NamedQueries({
        @NamedQuery(
                name = "getAllPurchase",
                query = "SELECT p FROM Purchase p ORDER BY p.id"
        )
})
@Entity
@Table(name = "PURCHASE")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Partner partner;
    @OneToMany(mappedBy = "purchase", fetch = FetchType.EAGER)
    private Set<Payment> payments;
    @ManyToMany
    private Set<Product> products;
    private Date release_date;
    private Double price;

    public Purchase() {
        payments = new LinkedHashSet<>();
        products = new LinkedHashSet<>();
    }

    public Purchase(Partner partner, Date release_date, Double price) {
        this.partner = partner;
        this.release_date = release_date;
        this.price = price;
        payments = new LinkedHashSet<>();
        products = new LinkedHashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
