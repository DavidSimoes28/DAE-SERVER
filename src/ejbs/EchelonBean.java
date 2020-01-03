package ejbs;

import entities.Echelon;
import entities.Modality;
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

@Stateless(name = "EchelonEJB")
public class EchelonBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    private ModalityBean modalityBean;

    public EchelonBean() {
    }

    public Echelon create(String name, int initialAge, int finalAge, int modalityId) throws Exception {
        Modality modality = modalityBean.find(modalityId);
        if(name.equals("") || initialAge <= 0 || finalAge<=0){
            throw new MyIllegalArgumentException("Echelon fields can't be empty");
        }
        if(initialAge >= finalAge){
            throw new MyIllegalArgumentException("Final Age bust be after Initial Age");
        }
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
            throw new MyEntityNotFoundException("ERROR_FINDING_ECHELON");
        }
    }

    public Echelon update(int id, int initialAge, int finalAge) throws Exception {
        Echelon echelon = em.find(Echelon.class, id);

        if(echelon == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_ECHELON");
        }

        try{
            em.lock(echelon, LockModeType.OPTIMISTIC);
            echelon.setInitialAge(initialAge);
            echelon.setFinalAge(finalAge);
            em.merge(echelon);
            return echelon;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_ECHELON");
        }
    }

    public boolean delete(int id) throws Exception{
        Echelon echelon = em.find(Echelon.class, id);

        if(echelon == null){
            throw new MyEntityNotFoundException("ERROR_FINDING_ECHELON");
        }

        try{
            em.lock(echelon, LockModeType.OPTIMISTIC);
            em.remove(echelon);
            return true;
        }catch (Exception e){
            throw new Exception("ERROR_FINDING_ECHELON");
        }
    }
}
