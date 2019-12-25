package dtos;

import java.io.Serializable;

public class PaymentDTO implements Serializable {
    private int id;
    private int purchaseId;
    private int quantity;
    private int stateId;

    public PaymentDTO() {
    }

    public PaymentDTO(int id, int purchaseId, int quantity, int stateId) {
        this.id = id;
        this.purchaseId = purchaseId;
        this.quantity = quantity;
        this.stateId = stateId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
}
