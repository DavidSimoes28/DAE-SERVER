package dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PurchaseDTO implements Serializable {
    private int id;
    private PartnerDTO partner;
    private List<ProductDTO> products;
    private Date release_date;
    private Double price;

    public PurchaseDTO() {
    }

    public PurchaseDTO(int id, PartnerDTO partner, Date release_date, Double price) {
        this.id = id;
        this.partner = partner;
        this.release_date = release_date;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PartnerDTO getPartner() {
        return partner;
    }

    public void setPartner(PartnerDTO partner) {
        this.partner = partner;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
