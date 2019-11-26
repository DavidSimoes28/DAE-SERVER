package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProductPayments",
                query = "SELECT s FROM ProductPayment s ORDER BY s.id"
        )
})
@Table(name = "PRODUCTPAYMENTS")
public class ProductPayment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Product product;
    private Date release_date;
    private int quantity;
    private Double price;
    private State state;
    private String receipt;
}
