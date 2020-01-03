package dtos;

import java.io.Serializable;

public class PaymentDTO implements Serializable {
    private int id;
    private int purchaseId;
    private int stateId;
    private int receiptId;
    private Double valueInEur;

    public PaymentDTO() {
    }

    public PaymentDTO(int id, int purchaseId, int stateId, Double valueInEur) {
        this.id = id;
        this.purchaseId = purchaseId;
        this.stateId = stateId;
        this.valueInEur = valueInEur;
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

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public Double getValueInEur() {
        return valueInEur;
    }

    public void setValueInEur(Double valueInEur) {
        this.valueInEur = valueInEur;
    }
}
