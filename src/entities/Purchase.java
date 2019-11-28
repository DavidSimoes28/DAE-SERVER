package entities;

import javax.persistence.*;
import java.util.Date;
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
    @ManyToMany
    private Set<Payment> payments;
    @ManyToMany
    private Set<Product> products;
    private Date release_date;
    private Double price;

    public Purchase() {
    }

}
