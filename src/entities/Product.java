package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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
    private Double valueInEur;
    private int stock;
    @ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
    private Set<Purchase> purchases;
    @ManyToOne
    private Product parentProduct;
    @OneToMany(mappedBy = "parentProduct")
    private List<Product> childrenProducts;
    private String table_name;

    public Product() {
        this.purchases = new LinkedHashSet<>();
        this.childrenProducts = new ArrayList<>();
    }

    public Product(ProductType type, String description, Double value, int stock, String table_name) {
        this.type = type;
        this.description = description;
        this.valueInEur = value;
        this.parentProduct = null;
        this.purchases = new LinkedHashSet<>();
        this.childrenProducts = new ArrayList<>();
        this.stock = stock;
        this.table_name = table_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValueInEur() {
        return valueInEur;
    }

    public void setValueInEur(Double value) {
        this.valueInEur = value;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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

    public Product getParentProduct() {
        return parentProduct;
    }

    public void setParentProduct(Product parentProduct) {
        this.parentProduct = parentProduct;
    }

    public List<Product> getChildrenProducts() {
        return childrenProducts;
    }

    public void setChildrenProducts(List<Product> childrenProducts) {
        this.childrenProducts = childrenProducts;
    }

    public void addChildrenProducts(Product childrenProduct) {
        this.childrenProducts.add(childrenProduct);
    }

    public void removeChildrenProducts(Product childrenProduct) {
        this.childrenProducts.remove(childrenProduct);
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }
}
