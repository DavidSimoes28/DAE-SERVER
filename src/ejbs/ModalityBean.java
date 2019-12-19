package ejbs;

import entities.Coach;
import entities.Modality;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.auth.Subject;
import java.util.List;
import java.util.Set;

@Stateless(name = "ModalityEJB")
public class ModalityBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    CoachBean coachBean;

    public ModalityBean() {
    }

    public Modality create(String name, int sportYear, boolean active) throws Exception {
        Modality modality = new Modality(name,sportYear,active);
        em.persist(modality);
        return modality;
    }

    public List<Modality> all() {
        try {
            return (List<Modality>) em.createNamedQuery("getAllModalities").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_STUDENTS", e);
        }
    }

    public Modality find(int id) throws Exception {
        try{
            return em.find(Modality.class, id);
        } catch (Exception e) {
            throw new Exception("ERROR_FINDING_ADMINISTRATOR", e);
        }
    }

    public Modality update(int id, String name) throws Exception {
        try{
            Modality modality = em.find(Modality.class, id);

            if(modality == null){
                throw new Exception("ERROR_FINDING_STUDENT");
            }

            //em.lock(administrator, LockModeType.OPTIMISTIC);
            modality.setName(name);
            em.merge(modality);
            return modality;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_STUDENT");
        }
    }

    public boolean delete(int id) throws Exception{
        try{
            Modality modality = em.find(Modality.class, id);

            if(modality == null){
                throw new Exception("ERROR_FINDING_STUDENT");
            }

            //em.lock(administrator, LockModeType.OPTIMISTIC);
            em.remove(modality);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_STUDENT");
        }
    }
}
