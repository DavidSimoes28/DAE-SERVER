package ejbs;

import entities.Modality;
import entities.Partner;
import entities.Payment;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Stateless(name = "PartnerEJB")
public class PartnerBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    private PurchaseBean purchaseBean;

    public PartnerBean(){

    }

    public Partner create(String username, String password, String name, String email) throws Exception {
        if (find(username)!=null){
            throw new MyEntityExistsException("Username '" + username + "' already exists");
        }
        if(username.equals("") || password.equals("") || name.equals("") || email.equals("")){
                throw new MyIllegalArgumentException("Partner fields can't be empty");
        }
        Partner partner = new Partner(username,password,name,email);
        em.persist(partner);
        return partner;
    }

    public List<Partner> all() {
        try {
            return (List<Partner>) em.createNamedQuery("getAllPartners").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ATHLETE", e);
        }
    }

    public Partner find(String username) throws MyEntityNotFoundException {
        try{
            return em.find(Partner.class, username);
        } catch (Exception e) {
            throw new  MyEntityNotFoundException("ERROR_FINDING_PARTNER");
        }
    }

    public Partner update(String username, String name, String email) throws Exception {
        Partner partner = em.find(Partner.class, username);

        if(partner == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_PARTNER");
        }
        if(username.equals("") || name.equals("") || email.equals("")){
            throw new MyIllegalArgumentException("Partner fields can't be empty");
        }
        try{
            em.lock(partner, LockModeType.OPTIMISTIC);
            partner.setName(name);
            partner.setEmail(email);
            em.merge(partner);
            return partner;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PARTNER");
        }
    }

    public boolean delete(String username) throws Exception{
        Partner partner = em.find(Partner.class, username);

        if(partner == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_PARTNER");
        }

        try{
            em.lock(partner, LockModeType.OPTIMISTIC);
            em.remove(partner);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PARTNER");
        }
    }

    public Set<Partner> filter(String username) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Partner> criteria = builder.createQuery(Partner.class);
        Root<Partner> from = criteria.from(Partner.class);
        criteria.select(from);

        if(!username.equals(null)){
            criteria.where(builder.equal(from.get("username"), username));
        }

        TypedQuery<Partner> query = em.createQuery(criteria);
        Set<Partner> result = new LinkedHashSet<>();

        if((username.equals("") || username.equals(null))){
            for (Partner partner : all()) {
                result.add(partner);
            }
            return result;
        }


        if(!username.equals(null) && !username.equals("")){
            for (Partner partner : query.getResultList()) {
                result.add(partner);
            }
        }

        return result;
    }
}
