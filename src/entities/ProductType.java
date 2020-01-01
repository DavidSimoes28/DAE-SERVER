package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@NamedQueries({
        @NamedQuery(
                name = "getAllProductTypes",
                query = "SELECT p FROM ProductType p ORDER BY p.type"
        )
})
@Entity
public class ProductType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String type;
    @OneToMany(mappedBy = "type")
    private Set<Product> products;
    @Version
    private int version;

    public ProductType() {
        this.products = new LinkedHashSet<>();
    }

    public ProductType(String type) {
        this.type = type;
        this.products = new LinkedHashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
