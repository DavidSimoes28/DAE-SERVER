package ejbs;

import entities.Payment;
import entities.Purchase;
import entities.State;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
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

    public Payment create(int stateId, int purchaseId, Double valueInEur) throws Exception {
        State state = stateBean.find(stateId);
        Purchase purchase = purchaseBean.find(purchaseId);
        Payment payment = new Payment(state,purchase, valueInEur);
        em.persist(payment);
        purchase.addPayment(payment);
        return payment;
    }

    public List<Payment> all() {
        try {
            return (List<Payment>) em.createNamedQuery("getAllPayments").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_PRODUCTS", e);
        }
    }

    public Payment find(int id) throws MyEntityNotFoundException {
        try{
            return em.find(Payment.class, id);
        } catch (Exception e) {
            throw new MyEntityNotFoundException("ERROR_FINDING_PRODUCT");
        }
    }

    public Payment update(int id, int stateId, Double valueInEur) throws Exception {
        Payment payment = em.find(Payment.class, id);
        State state = stateBean.find(stateId);
        if(payment == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_PRODUCT");
        }
        if(valueInEur <= 0){
            throw new MyIllegalArgumentException("INVALID_VALUE");
        }

        try{
            em.lock(payment, LockModeType.OPTIMISTIC);
            payment.setValueInEur(valueInEur);
            payment.setState(state);
            em.merge(payment);
            return payment;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PRODUCT");
        }
    }
}
