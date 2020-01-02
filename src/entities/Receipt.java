package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "RECEIPTS")
@NamedQueries({
        @NamedQuery(name = "getPaymentReceipt", query = "SELECT doc FROM Receipt doc WHERE doc.id = :id")
})
public class Receipt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String filepath;

    private String filename;

    @OneToMany
    private Set<Payment> payments;

    public Receipt() {
        payments = new LinkedHashSet<>();
    }

    public Receipt(String filepath, String filename, Set<Payment> payments) {
        this.filepath = filepath;
        this.filename = filename;
        this.payments = new LinkedHashSet<>();
        this.payments = payments;
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

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }
}
