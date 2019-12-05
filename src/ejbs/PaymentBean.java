package ejbs;

import entities.Payment;
import entities.Purchase;
import entities.State;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Stateless(name = "PaymentEJB")
public class PaymentBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    private StateBean stateBean;
    @EJB
    private PurchaseBean purchaseBean;

    public PaymentBean() {
    }

    public Payment create(int quantity, String stateName, int purchaseId) throws Exception {
        State state = stateBean.find(stateName);
        Purchase purchase = purchaseBean.find(purchaseId);
        Payment payment = new Payment(quantity,state,purchase);
        em.persist(payment);
        return payment;
    }

    public List<Payment> all() {
        try {
            return (List<Payment>) em.createNamedQuery("getAllPayments").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_PRODUCTS", e);
        }
    }

    public Payment find(int id) throws Exception {
        try{
            return em.find(Payment.class, id);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_PRODUCT", e);
        }
    }

    public Payment update(int id, int quantity, String stateName) throws Exception {
        try{
            Payment payment = em.find(Payment.class, id);
            State state = stateBean.find(stateName);
            if(payment == null){
                throw new Exception("ERROR_FINDING_PRODUCT");
            }

            payment.setQuantity(quantity);
            payment.setState(state);

            em.merge(payment);
            return payment;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PRODUCT");
        }
    }
}
