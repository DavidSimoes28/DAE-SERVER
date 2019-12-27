package ejbs;

import entities.Modality;
import entities.Partner;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "PartnerEJB")
public class PartnerBean {
    @PersistenceContext
    private EntityManager em;

    public PartnerBean(){

    }

    public Partner create(String username, String password, String name, String email) throws Exception {
        if (find(username)!=null){
            throw new Exception("Username '" + username + "' already exists");
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

    public Partner update(String username, String password, String name, String email) throws Exception {
        try{
            Partner partner = em.find(Partner.class, username);

            if(partner == null){
                throw new Exception("ERROR_FINDING_PARTNER");
            }

            //em.lock(administrator, LockModeType.OPTIMISTIC);
            partner.setName(name);
            partner.setEmail(email);
            partner.setPassword(password);
            em.merge(partner);
            return partner;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PARTNER");
        }
    }

    public boolean delete(String username) throws Exception{
        try{
            Partner partner = em.find(Partner.class, username);

            if(partner == null){
                throw new Exception("ERROR_FINDING_PARTNER");
            }

            //em.lock(coach, LockModeType.OPTIMISTIC);
            em.remove(partner);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_PARTNER");
        }
    }
}
