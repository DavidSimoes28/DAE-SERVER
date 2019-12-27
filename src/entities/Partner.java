package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPartners",
                query = "SELECT pa FROM Partner pa ORDER BY pa.name"
        )
})
public class Partner extends User {
    @OneToMany(mappedBy = "partner", cascade = CascadeType.REMOVE)
    private Set<Purchase> purchases;
    private Double quota;


    public Partner() {
        purchases = new LinkedHashSet<>();
    }

    public Partner(String username, String password, String name, String email) {
        super(username, password, name, email);
        purchases = new LinkedHashSet<>();
    }

    public Double getQuota() {
        return quota;
    }

    public void setQuota(Double quota) {
        this.quota = quota;
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
}
