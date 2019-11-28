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
    @ManyToMany(mappedBy = "athletes", fetch = FetchType.EAGER)
    private Set<Modality> modalities;

    public Partner() {
        modalities = new LinkedHashSet<>();
        purchases = new LinkedHashSet<>();
    }

    public Partner(String username, String password, String name, String email) {
        super(username, password, name, email);
        modalities = new LinkedHashSet<>();
        purchases = new LinkedHashSet<>();
    }

    public Set<Modality> getModalities() {
        return modalities;
    }

    public void setModalities(Set<Modality> modalities) {
        this.modalities = modalities;
    }

    public void addModality(Modality modality) {
        this.modalities.add(modality);
    }

    public void removeModality(Modality modality) {
        this.modalities.remove(modality);
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
