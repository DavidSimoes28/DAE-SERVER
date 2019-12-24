package ejbs;

import entities.Partner;
import entities.Purchase;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Stateless(name = "PurchaseEJB")
public class PurchaseBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    private PartnerBean partnerBean;

    public PurchaseBean() {
    }
    public Purchase create(String username, Date releaseDate, Double price) throws Exception {
        Partner partner = partnerBean.find(username);
        Purchase purchase = new Purchase(partner,releaseDate,price);
        em.persist(purchase);
        return purchase;
    }

    public List<Purchase> all() {
        try {
            return (List<Purchase>) em.createNamedQuery("getAllProducts").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_PRODUCTS", e);
        }
    }

    public Purchase find(int id) throws Exception {
        try{
            return em.find(Purchase.class, id);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_PRODUCT", e);
        }
    }

    public Purchase update(int id, Date releaseDate, Double price) throws Exception {
        try{
            Purchase purchase = em.find(Purchase.class, id);
            if(purchase == null){
                throw new Exception("ERROR_FINDING_PRODUCT");
            }

            purchase.setReleaseDate(releaseDate);
            purchase.setPrice(price);
            em.merge(purchase);
            return purchase;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PRODUCT");
        }
    }
}
