package dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PurchaseDTO implements Serializable {
    private int id;
    private String partnerUsername;
    private List<ProductDTO> products;
    private String releaseDate;
    private Double price;

    public PurchaseDTO() {
    }

    public PurchaseDTO(int id, String partnerUsername, String releaseDate, Double price) {
        this.id = id;
        this.partnerUsername = partnerUsername;
        this.releaseDate = releaseDate;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartnerUsername() {
        return partnerUsername;
    }

    public void setPartnerUsername(String partnerUsername) {
        this.partnerUsername = partnerUsername;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
