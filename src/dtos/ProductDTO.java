package dtos;

import java.io.Serializable;
import java.util.List;

public class ProductDTO implements Serializable {
    private int id;
    private ProductTypeDTO type;
    private String description;
    private Double valueInEur;
    private int stock;
    private List<PurchaseDTO> purchases;

    public ProductDTO() {
    }

    public ProductDTO(int id, ProductTypeDTO type, String description, Double valueInEur, int stock) {
        this.id = id;
        this.type = type;
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

    public ProductTypeDTO getType() {
        return type;
    }

    public void setType(ProductTypeDTO type) {
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
