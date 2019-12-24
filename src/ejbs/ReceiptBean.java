package ejbs;

import entities.Receipt;
import entities.Payment;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "ReceiptEJB")
public class ReceiptBean {
    @PersistenceContext
    EntityManager em;

    public void create(int id, String filepath, String fileName) throws MyEntityNotFoundException{
        try {
            Payment payment = em.find(Payment.class, id);
            if(payment == null){
                throw new MyEntityNotFoundException("Payment with id: " + id + " not found");
            }
            Receipt receipt = new Receipt(filepath, fileName, payment);
            em.persist(receipt);
            payment.setReceipt(receipt);
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

    public Receipt getPaymentReceipt(int id) {
        try{
            return em.createNamedQuery("getPaymentReceipt", Receipt.class).setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_DOCUMENT ----> ", e);
        }
    }


}
