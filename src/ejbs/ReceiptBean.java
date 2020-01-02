package ejbs;

import entities.Purchase;
import entities.Receipt;
import entities.Payment;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "ReceiptEJB")
public class ReceiptBean {
    @PersistenceContext
    EntityManager em;
    @EJB
    StateBean stateBean;

    public void create(int purchaseId, int paymentId ,String filepath, String fileName) throws MyEntityNotFoundException{
        try {
            Purchase purchase = em.find(Purchase.class, purchaseId);
            if(purchase == null){
                throw new MyEntityNotFoundException("Purchase with id: " + purchaseId + " not found");
            }
            Payment payment = em.find(Payment.class, paymentId);
            if (payment == null) {
                throw new MyEntityNotFoundException("Payment with id " + paymentId + " not found.");
            }

            Receipt receipt = new Receipt(filepath, fileName, purchase.getPayments());
            em.persist(receipt);

            payment.setReceipt(receipt);
            if(stateBean.getNotPaidState().getId() == payment.getId()){
                payment.setState(stateBean.getPaidState());
            }

        } catch (MyEntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_DOCUMENT ----> ", e);
        }
    }

    public Receipt findReceipt(int id) {
        try{
            return em.find(Receipt.class, id);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_DOCUMENT ----> ", e);
        }
    }

    /*public Receipt getPaymentReceipt(int id) {
        try{
            return em.createNamedQuery("getPaymentReceipt", Receipt.class).setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_DOCUMENT ----> ", e);
        }
    }*/


}
