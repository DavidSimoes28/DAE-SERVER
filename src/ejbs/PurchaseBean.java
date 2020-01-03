package ejbs;

import entities.Partner;
import entities.Payment;
import entities.Product;
import entities.Purchase;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Stateless(name = "PurchaseEJB")
public class PurchaseBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    private PartnerBean partnerBean;
    @EJB
    private ProductBean productBean;

    public PurchaseBean() {
    }

    public Purchase create(String username, String releaseDateString, Double price) throws Exception {
        Partner partner = partnerBean.find(username);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate = format.parse(releaseDateString);
        Purchase purchase = new Purchase(partner,releaseDate,price);
        em.persist(purchase);
        return purchase;
    }

    public Purchase addProduct(int id, int productId) throws Exception {
        Product product = productBean.find(productId);
        Purchase purchase = find(id);
        purchase.addProduct(product);
        product.addPurchase(purchase);
        return purchase;
    }

    public Purchase removeProduct(int id, int productId) throws Exception {
        Product product = productBean.find(productId);
        Purchase purchase = find(id);
        purchase.removeProduct(product);
        product.removePurchase(purchase);
        return purchase;
    }

    public List<Purchase> all() {
        try {
            return (List<Purchase>) em.createNamedQuery("getAllPurchase").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_PRODUCTS", e);
        }
    }

    public Purchase find(int id) throws Exception {
        try{
            return em.find(Purchase.class, id);
        } catch (Exception e) {
            throw new MyEntityNotFoundException("ERROR_FINDING_PRODUCT");
        }
    }

    public List<Payment> findPurchasePayments(int id) throws Exception {
        List<Payment> payments = new ArrayList<>();
        Purchase purchase = find(id);
        if(!purchase.getPayments().isEmpty())
            payments.addAll(purchase.getPayments());

        return payments;
    }

    public List<Payment> findPartnerPayments(String username) {
        List<Purchase> allPayments = all();
        List<Payment> payments = new ArrayList<>();
        for (Purchase purchase : allPayments) {
            if(purchase.getPartner().getUsername().equals(username) && !purchase.getPayments().isEmpty())
                for (Payment payment : purchase.getPayments()) {
                    payments.add(payment);
                }
        }
        return payments;
    }

    public Purchase update(int id, Double price) throws Exception {
        Purchase purchase = em.find(Purchase.class, id);
        if(purchase == null){
            throw new Exception("ERROR_FINDING_PRODUCT");
        }
        try{
            em.lock(purchase, LockModeType.OPTIMISTIC);
            purchase.setPrice(price);
            em.merge(purchase);
            return purchase;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PRODUCT");
        }
    }
}
