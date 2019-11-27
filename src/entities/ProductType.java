package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@NamedQueries({
        @NamedQuery(
                name = "getAllProductTypes",
                query = "SELECT p FROM ProductType p ORDER BY p.id"
        )
})
@Entity
public class ProductType implements Serializable {
    @Id
    private int id;
    private int type;
    @OneToMany(mappedBy = "type")
    private Set<Product> products;

    public ProductType() {
        this.products = new LinkedHashSet<>();
    }

    public ProductType(int id, int type) {
        this.id = id;
        this.type = type;
        this.products = new LinkedHashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
