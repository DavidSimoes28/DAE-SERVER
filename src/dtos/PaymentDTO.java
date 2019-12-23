package dtos;

import java.io.Serializable;

public class PaymentDTO implements Serializable {
    private int id;
    private PurchaseDTO purchase;
    private int quantity;
    private StateDTO state;

    public PaymentDTO() {
    }

    public PaymentDTO(int id, PurchaseDTO purchase, int quantity, StateDTO state) {
        this.id = id;
        this.purchase = purchase;
        this.quantity = quantity;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PurchaseDTO getPurchase() {
        return purchase;
    }

    public void setPurchase(PurchaseDTO purchase) {
        this.purchase = purchase;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public StateDTO getState() {
        return state;
    }

    public void setState(StateDTO state) {
        this.state = state;
    }
}
