package dtos;

public class FilterProductDTO {
    int id;
    int productTypeId;


    public FilterProductDTO() {
    }

    public FilterProductDTO(int id, int productTypeId) {
        this.id = id;
        this.productTypeId = productTypeId;
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
}
