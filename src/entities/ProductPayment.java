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
    @OneToOne
    private User utilizador;
    @OneToOne
    private Product Produto;
    private Date data_lancamento;
    private int quantidade;
    private Double preco;
    private State estado;
    private String recibo;
}
