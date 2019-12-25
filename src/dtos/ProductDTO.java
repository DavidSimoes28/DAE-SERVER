package dtos;

import java.io.Serializable;
import java.util.List;

public class ProductDTO implements Serializable {
    private int id;
    private int productTypeId;
    private String description;
    private Double valueInEur;
    private int stock;
    private List<PurchaseDTO> purchases;

    public ProductDTO() {
    }

    public ProductDTO(int id, int productTypeId, String description, Double valueInEur, int stock) {
        this.id = id;
        this.productTypeId = productTypeId;
        this.description = description;
        this.valueInEur = valueInEur;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
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

    public void setValueInEur(Double valueInEur) {
        this.valueInEur = valueInEur;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<PurchaseDTO> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseDTO> purchases) {
        this.purchases = purchases;
    }
}
