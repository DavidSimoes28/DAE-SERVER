package entities;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
@NamedQueries({
        @NamedQuery(
                name = "getAllPurchase",
                query = "SELECT p FROM Purchase p ORDER BY p.releaseDate"
        )
})
@Entity
@Table(name = "PURCHASE")
public class
Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Partner partner;
    @OneToMany(mappedBy = "purchase", fetch = FetchType.EAGER)
    private Set<Payment> payments;
    @ManyToMany
    private Set<Product> products;
    private Date releaseDate;
    private Double price;
    @Version
    private int version;

    public Purchase() {
        payments = new LinkedHashSet<>();
        products = new LinkedHashSet<>();
    }

    public Purchase(Partner partner, Date releaseDate, Double price) {
        this.partner = partner;
        this.releaseDate = releaseDate;
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
