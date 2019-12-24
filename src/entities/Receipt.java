package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "RECEIPTS")
@NamedQueries({
        @NamedQuery(name = "getPaymentReceipt", query = "SELECT doc FROM Receipt doc WHERE doc.payment.id = :id")
})
public class Receipt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String filepath;

    private String filename;

    @OneToOne
    private Payment payment;

    public Receipt() {
    }

    public Receipt(String filepath, String filename, Payment payment) {
        this.filepath = filepath;
        this.filename = filename;
        this.payment = payment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String desiredName) {
        this.filename = desiredName;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
