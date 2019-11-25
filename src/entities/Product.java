package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProducts",
                query = "SELECT s FROM Product s ORDER BY s.id"
        )
})
@Table(name = "PRODUCTS")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private ProductType type;
    private String description;
    private Double value;
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private Set<ProductPayment> productPayments;
}
