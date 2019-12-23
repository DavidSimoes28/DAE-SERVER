package ejbs;

import entities.Echelon;
import entities.Modality;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Stateless(name = "EchelonEJB")
public class EchelonBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    private ModalityBean modalityBean;

    public EchelonBean() {
    }

    public Echelon create(String name, int initialAge, int finalAge, int modality_id) throws Exception {
        Modality modality = modalityBean.find(modality_id);
        Echelon echelon = new Echelon(name,initialAge,finalAge,modality);
        em.persist(echelon);
        modality.addEchelon(echelon);
        return echelon;
    }

    public List<Echelon> all() {
        try {
            return (List<Echelon>) em.createNamedQuery("getAllEchelons").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ECHELONS", e);
        }
    }

    public Echelon find(int id) throws Exception {
        try{
            return em.find(Echelon.class, id);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_ECHELON", e);
        }
    }

    public Echelon update(int id,String name, int initialAge, int finalAge) throws Exception {
        try{
            Echelon echelon = em.find(Echelon.class, id);

            if(echelon == null){
                throw new Exception("ERROR_FINDING_STATE");
            }

            echelon.setInitialAge(initialAge);
            echelon.setFinalAge(finalAge);
            em.merge(echelon);
            return echelon;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_ECHELON");
        }
    }

    public boolean delete(int id) throws Exception{
        try{
            Echelon echelon = em.find(Echelon.class, id);

            if(echelon == null){
                throw new Exception("ERROR_FINDING_ECHELON");
            }

            em.remove(echelon);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_ECHELON");
        }
    }
}
